package ak.main.Behaviours.Coordinator;

import ak.main.Agents.Constants.AgentNames;
import ak.main.Agents.CoordinatorAgent;
import ak.main.Ontology.CarFactoryOntology;
import ak.main.Ontology.Constants.MachineResponse;
import ak.main.Ontology.Constants.MachineStatus;
import ak.main.Ontology.Constants.MachineType;
import ak.main.Ontology.Constants.Materials;
import ak.main.Ontology.Dto.MachineResponsesDto;
import ak.main.Ontology.Dto.MaintenanceRequestDto;
import ak.main.Ontology.Orders.MaterialRequest;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

//This behaviour checks the machine statuses and based on the machine responses the coordinator agent:
//1. Will do nothing, or
//2. Will check if there is any maintenance agent available and if there is they will send a repair request to them, or,
//3. Will request supplies from the warehouse agent
public class MachineStatusInspector extends CyclicBehaviour {
    private static LinkedList<MaintenanceRequestDto> maintenanceRequestQueue = new LinkedList<>();
    private boolean isRetryScheduled = false;

    public MachineStatusInspector(CoordinatorAgent agent) {
        super(agent);
    }

    public void action() {
        MessageTemplate template = MessageTemplate.and(
                MessageTemplate.MatchOntology(CarFactoryOntology.CAR_FACTORY_ONTOLOGY),
                MessageTemplate.MatchPerformative(ACLMessage.INFORM)
        );

        MessageTemplate triggerTemplate = MessageTemplate.MatchConversationId("trigger-queue-processing");
        ACLMessage triggerMsg = myAgent.receive(triggerTemplate);
        if (triggerMsg != null) {
            try {
                processQueuedRequests(); // modify method to not need params
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }

        ACLMessage msg = myAgent.receive(template);
        if (msg != null && msg.getSender().getLocalName().startsWith("MachineAgent_")) {
            try {
                MachineResponsesDto machineResponseDto = (MachineResponsesDto) msg.getContentObject();
                if ((((CoordinatorAgent) myAgent).getMachineStatus(machineResponseDto.getMachineType()) == null
                        || ((CoordinatorAgent) myAgent).getMachineStatus(machineResponseDto.getMachineType()).equals(MachineStatus.OPERATING))
                        && !machineResponseDto.getMachineResponses().isEmpty()) {
                    for (MachineResponse m : machineResponseDto.getMachineResponses()) {
                        switch (m) {
                            case MachineResponse.ALUMINIUM_LEVEL_LOW,
                                 MachineResponse.NO_ALUMINIUM -> handleMaterialRequest(Materials.ALUMINUM, m);
                            case MachineResponse.PAINT_LEVEL_LOW,
                                 MachineResponse.NO_PAINT -> handleMaterialRequest(Materials.PAINT, m);
                            case MachineResponse.PLASTIC_LEVEL_LOW,
                                 MachineResponse.NO_PLASTIC -> handleMaterialRequest(Materials.PLASTIC, m);
                            case MachineResponse.PACKAGING_MATERIAL_LOW,
                                 MachineResponse.PACKAGING_MATERIAL_EMPTY ->
                                    handleMaterialRequest(Materials.PACKAGING, m);
                            case MachineResponse.LOW_FURNACE_TEMP,
                                 MachineResponse.CAR_BODY_BOX_CAPACITY_EMPTY,
                                 MachineResponse.CAR_BODY_BOX_CAPACITY_FULL,
                                 MachineResponse.CAR_WHEEL_BOX_CAPACITY_EMPTY,
                                 MachineResponse.CAR_WHEEL_BOX_CAPACITY_FULL,
                                 MachineResponse.CAR_INTERIOR_CHASSIS_BOX_CAPACITY_EMPTY,
                                 MachineResponse.CAR_INTERIOR_CHASSIS_BOX_CAPACITY_FULL,
                                 MachineResponse.PAINT_NOZZLE_BLOCKED,
                                 MachineResponse.PACKAGING_JAM,
                                 MachineResponse.MALFUNCTIONING_PRESS,
                                 MachineResponse.SERVO_MALFUNCTION ->
                                    handleMaintenanceRequest(machineResponseDto.getMachineType(), m);
                        }
                    }
                } else {
                    ((CoordinatorAgent) myAgent).changeMachineStatus(machineResponseDto.getMachineType(), MachineStatus.OPERATING);
                }
            } catch (UnreadableException | IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            block();
        }
    }

    private void handleMaterialRequest(Materials material, MachineResponse machineResponse) throws IOException {
        ACLMessage materialRequest = new ACLMessage(ACLMessage.REQUEST);
        materialRequest.addReceiver(new AID(AgentNames.WAREHOUSE_AGENT.getAgentName(), AID.ISLOCALNAME));
        materialRequest.setOntology(CarFactoryOntology.CAR_FACTORY_ONTOLOGY);
        materialRequest.setContentObject(new MaterialRequest()
                .setMaterial(material)
                .setResponse(machineResponse));
        myAgent.send(materialRequest);
        System.out.println("Order for: " + material + " has been sent");
    }

    //We will make a call for proposal (CFP) in order to first determine if there is any maintenance agent available
    //before sending a repair request.
    public void handleMaintenanceRequest(MachineType machineType, MachineResponse machineResponse) throws IOException {
        List<AID> availableMaintenanceAgents = ((CoordinatorAgent) myAgent).getAvailableMaintenanceAgents();

        if (availableMaintenanceAgents == null || availableMaintenanceAgents.isEmpty()) {
            MaintenanceRequestDto request = new MaintenanceRequestDto()
                    .setMachineType(machineType)
                    .setMachineResponse(machineResponse);
            maintenanceRequestQueue.add(request);
            System.out.println("Maintenance request for " + machineType + " has been queued.");

            myAgent.addBehaviour(new WakerBehaviour(myAgent, 5000) {
                @Override
                protected void onWake() {
                    try {
                        processQueuedRequests();  // Retry processing the requests after delay
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            return;
        }

        processMaintenanceRequest(machineType, machineResponse, availableMaintenanceAgents);
    }

    public void processQueuedRequests() throws IOException {
        List<AID> availableMaintenanceAgents = ((CoordinatorAgent) myAgent).getAvailableMaintenanceAgents();

        if (availableMaintenanceAgents == null || availableMaintenanceAgents.isEmpty()) {
            if (!isRetryScheduled) {
                isRetryScheduled = true;
                // If no agents are still available, retry after 10 seconds
                myAgent.addBehaviour(new WakerBehaviour(myAgent, 10000) {
                    @Override
                    protected void onWake() {
                        try {
                            processQueuedRequests();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
            return;
        }

        // Process requests in the queue
        Iterator<MaintenanceRequestDto> iterator = maintenanceRequestQueue.iterator();
        while (iterator.hasNext()) {
            MaintenanceRequestDto request = iterator.next();

            processMaintenanceRequest(request.getMachineType(), request.getMachineResponse(), availableMaintenanceAgents);

            // Remove the processed request from the queue
            iterator.remove();
        }

        if (!maintenanceRequestQueue.isEmpty() && !isRetryScheduled) {
            isRetryScheduled = true;
            myAgent.addBehaviour(new WakerBehaviour(myAgent, 10000) {
                @Override
                protected void onWake() {
                    isRetryScheduled = false;
                    try {
                        processQueuedRequests();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
    }

    private void processMaintenanceRequest(MachineType machineType, MachineResponse machineResponse, List<AID> availableMaintenanceAgents) throws IOException {
        String conversationId = "maintenance-" + machineType.getMachineName() + "-" + System.currentTimeMillis();

        ACLMessage repairCFP = new ACLMessage(ACLMessage.CFP);
        repairCFP.setOntology(CarFactoryOntology.CAR_FACTORY_ONTOLOGY);
        repairCFP.setLanguage(FIPANames.ContentLanguage.FIPA_SL);
        repairCFP.setContentObject(new MaintenanceRequestDto()
                .setMachineType(machineType)
                .setMachineResponse(machineResponse));
        repairCFP.setConversationId(conversationId);
        repairCFP.addReceiver(availableMaintenanceAgents.getFirst());
        repairCFP.setReplyWith("repairCfp" + System.currentTimeMillis());

        System.out.println("Maintenance CFP for: " + machineType + " has been sent.");

        myAgent.addBehaviour(new MaintenanceRepairContractNetInitiator(myAgent, repairCFP, machineType, machineResponse, this));
    }
}
