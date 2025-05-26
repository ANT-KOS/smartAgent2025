package ak.main.Behaviours;

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
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

import java.io.IOException;

public class MachineStatusInspector extends CyclicBehaviour {
    public MachineStatusInspector(CoordinatorAgent agent) {
        super(agent);
    }

    public void action() {
        MessageTemplate template = MessageTemplate.and(
                MessageTemplate.MatchOntology(CarFactoryOntology.CAR_FACTORY_ONTOLOGY),
                MessageTemplate.MatchPerformative(ACLMessage.INFORM)
        );

        ACLMessage msg = myAgent.receive(template);
        if (msg != null && msg.getSender().getLocalName().startsWith("MachineAgent_")) {
            try {
                MachineResponsesDto machineResponseDto = (MachineResponsesDto) msg.getContentObject();
                if (!machineResponseDto.getMachineResponses().isEmpty()) {
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
                                 MachineResponse.SERVO_MALFUNCTION,
                                 MachineResponse.DETECTION_FAILURE
                                 -> handleMaintenanceRequest(machineResponseDto.getMachineType(), m);
                        }
                    }
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

    private void handleMaintenanceRequest(MachineType machineType, MachineResponse machineResponse) throws IOException {
        String conversationId = "maintenance-" + machineType.getMachineName() + "-" + System.currentTimeMillis();

        ACLMessage maintenanceRequest = new ACLMessage(ACLMessage.REQUEST);
        maintenanceRequest.addReceiver(new AID(AgentNames.MAINTENANCE_AGENT.getAgentName(), AID.ISLOCALNAME));
        maintenanceRequest.setOntology(CarFactoryOntology.CAR_FACTORY_ONTOLOGY);
        maintenanceRequest.setLanguage(FIPANames.ContentLanguage.FIPA_SL);
        maintenanceRequest.setContentObject(new MaintenanceRequestDto()
                .setMachineType(machineType)
                .setMachineResponse(machineResponse));
        maintenanceRequest.setConversationId(conversationId);
        maintenanceRequest.setReplyWith(conversationId);
        myAgent.send(maintenanceRequest);

        ((CoordinatorAgent) myAgent).changeMachineStatus(machineType, MachineStatus.FAULTY);
    }
}
