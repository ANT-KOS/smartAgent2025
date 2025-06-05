package ak.main.Behaviours.Maintenance;

import ak.main.Agents.Constants.AgentNames;
import ak.main.Ontology.CarFactoryOntology;
import ak.main.Ontology.Constants.MachineType;
import ak.main.Ontology.Dto.MaintenanceRequestDto;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class MaintenanceRequestReceiver extends CyclicBehaviour {
    private boolean busyRepairing = false;

    public MaintenanceRequestReceiver(Agent agent) {
        super(agent);
    }

    @Override
    public void action() {
        MessageTemplate cfpMessageTemplate = MessageTemplate.and(
                MessageTemplate.MatchPerformative(ACLMessage.CFP),
                MessageTemplate.MatchSender(new AID(AgentNames.COORDINATOR_AGENT.getAgentName(), AID.ISLOCALNAME))
        );

        MessageTemplate repairMessageTemplate = MessageTemplate.and(
                MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
                MessageTemplate.MatchSender(new AID(AgentNames.COORDINATOR_AGENT.getAgentName(), AID.ISLOCALNAME))
        );

        ACLMessage cfp = myAgent.receive(cfpMessageTemplate);

        if (cfp != null) {
            handleCfp(cfp);
        } else {
            ACLMessage repair = myAgent.receive(repairMessageTemplate);
            if (repair != null) {
                handleRepairRequest(repair);
            } else  {
                block();
            }
        }
    }

    private void handleCfp(ACLMessage cfpMessage) {
        try {
            if (!busyRepairing) {
                ACLMessage proposal = cfpMessage.createReply();
                proposal.setPerformative(ACLMessage.PROPOSE);
                proposal.setContent("I can repair the machine.");
                myAgent.send(proposal);
                System.out.println("Proposed to repair the machine.");

                busyRepairing = true;

                myAgent.addBehaviour(new OneShotBehaviour(myAgent) {
                    @Override
                    public void action() {
                        busyRepairing = false;
                        System.out.println("Repair completed. The agent is now available again.");
                    }
                });
            } else {
                ACLMessage refuseMessage = cfpMessage.createReply();
                refuseMessage.setPerformative(ACLMessage.REFUSE);
                refuseMessage.setContent("I'm busy and cannot handle the repair right now.");
                myAgent.send(refuseMessage);
                System.out.println("Refused the repair request as the agent is busy.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleRepairRequest(ACLMessage repairRequestMessage) {
        try {
            MaintenanceRequestDto maintenanceRequestDto = (MaintenanceRequestDto) repairRequestMessage.getContentObject();
            String conversationId = repairRequestMessage.getConversationId();

            System.out.println("Received maintenance request for machine: " + maintenanceRequestDto.getMachineType().getMachineName());

            ACLMessage response = new ACLMessage(ACLMessage.INFORM);
            response.addReceiver(repairRequestMessage.getSender());
            response.setOntology(CarFactoryOntology.CAR_FACTORY_ONTOLOGY);
            response.setLanguage(FIPANames.ContentLanguage.FIPA_SL);
            response.setConversationId(conversationId);
            response.setReplyWith(conversationId);
            response.setContent("REPAIRS STARTED for machine: " + maintenanceRequestDto.getMachineType().getMachineName());
            myAgent.send(response);
            handleStopRequest(maintenanceRequestDto);

            maintenanceRequestDto.getMachineType().getMachine().repair();

            myAgent.addBehaviour(new OneShotBehaviour(myAgent) {
                @Override
                public void action() {
                    ACLMessage repairCompletedMessage = new ACLMessage(ACLMessage.INFORM);
                    repairCompletedMessage.addReceiver(repairRequestMessage.getSender());
                    repairCompletedMessage.setOntology(CarFactoryOntology.CAR_FACTORY_ONTOLOGY);
                    repairCompletedMessage.setLanguage(FIPANames.ContentLanguage.FIPA_SL);
                    repairCompletedMessage.setContent("REPAIRED machine: " + maintenanceRequestDto.getMachineType().getMachineName());
                    repairCompletedMessage.setConversationId(repairRequestMessage.getConversationId());
                    repairCompletedMessage.setReplyWith(repairRequestMessage.getConversationId());
                    myAgent.send(repairCompletedMessage);
                    System.out.println("Repair completed for machine: " +  maintenanceRequestDto.getMachineType().getMachineName());
                    handleStartRequest(maintenanceRequestDto);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleStopRequest(MaintenanceRequestDto maintenanceRequestDto) {
        sendStopRequest(maintenanceRequestDto.getMachineType());
        switch (maintenanceRequestDto.getMachineResponse()) {
            case PAINT_NOZZLE_BLOCKED -> {
                sendStopRequest(MachineType.CAR_ASSEMBLY);
                sendStopRequest(MachineType.CAR_PACKAGING);
            }
            case SERVO_MALFUNCTION -> sendStopRequest(MachineType.CAR_PACKAGING);
        }
    }

    private void sendStopRequest(MachineType machineType) {
        String machineName = machineType.getMachineName();
        ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
        request.addReceiver(new AID(AgentNames.MACHINE_AGENT.getAgentName(machineName), AID.ISLOCALNAME));
        request.setContent("STOP " + machineName);
        request.setConversationId("machineStop_" + machineName + "_" + System.currentTimeMillis());
        myAgent.send(request);
        System.out.println("Machine stop request for " + machineName + " sent");
    }

    private void handleStartRequest(MaintenanceRequestDto maintenanceRequestDto) {
        sendStartRequest(maintenanceRequestDto.getMachineType());
        switch (maintenanceRequestDto.getMachineResponse()) {
            case PAINT_NOZZLE_BLOCKED -> {
                sendStartRequest(MachineType.CAR_ASSEMBLY);
                sendStartRequest(MachineType.CAR_PACKAGING);
            }
            case SERVO_MALFUNCTION -> sendStartRequest(MachineType.CAR_PACKAGING);
        }
    }

    private void sendStartRequest(MachineType machineType) {
        String machineName = machineType.getMachineName();
        ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
        request.addReceiver(new AID(AgentNames.MACHINE_AGENT.getAgentName(machineName), AID.ISLOCALNAME));
        request.setContent("START " + machineName);
        request.setConversationId("machineStart");
        myAgent.send(request);
        System.out.println("Machine " + machineName + " started");
    }
}
