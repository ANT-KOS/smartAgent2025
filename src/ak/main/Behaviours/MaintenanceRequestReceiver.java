package ak.main.Behaviours;

import ak.main.Agents.Constants.AgentNames;
import ak.main.Ontology.CarFactoryOntology;
import ak.main.Ontology.Constants.MachineType;
import ak.main.Ontology.Dto.MaintenanceRequestDto;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class MaintenanceRequestReceiver extends CyclicBehaviour {
    public MaintenanceRequestReceiver(Agent agent) {
        super(agent);
    }

    public void action() {
        MessageTemplate template = MessageTemplate.and(
                MessageTemplate.MatchSender(new AID(AgentNames.COORDINATOR_AGENT.getAgentName(), AID.ISLOCALNAME)),
                MessageTemplate.MatchPerformative(ACLMessage.REQUEST)
        );

        ACLMessage msg = myAgent.receive(template);
        if (msg != null) {
            try {
                MaintenanceRequestDto maintenanceRequestDto = (MaintenanceRequestDto) msg.getContentObject();
                String conversationId = msg.getConversationId();

                System.out.println("Received maintenance request for machine " + maintenanceRequestDto.getMachineType().getMachineName());
                ACLMessage response = new ACLMessage(ACLMessage.INFORM);
                response.addReceiver(msg.getSender());
                response.setOntology(CarFactoryOntology.CAR_FACTORY_ONTOLOGY);
                response.setLanguage(FIPANames.ContentLanguage.FIPA_SL);
                response.setConversationId(conversationId);
                response.setReplyWith(conversationId);
                response.setContent("REPAIRS STARTED for machine: " + maintenanceRequestDto.getMachineType().getMachineName());
                myAgent.send(response);
                handleStopRequest(maintenanceRequestDto);

                maintenanceRequestDto.getMachineType().getMachine().repair();

                ACLMessage repairCompletedResponse = new ACLMessage(ACLMessage.INFORM);
                repairCompletedResponse.addReceiver(msg.getSender());
                repairCompletedResponse.setOntology(CarFactoryOntology.CAR_FACTORY_ONTOLOGY);
                repairCompletedResponse.setLanguage(FIPANames.ContentLanguage.FIPA_SL);
                repairCompletedResponse.setConversationId(conversationId);
                repairCompletedResponse.setReplyWith(conversationId);
                repairCompletedResponse.setContent("REPAIRED for machine: " + maintenanceRequestDto.getMachineType().getMachineName());
                myAgent.send(repairCompletedResponse);
                handleStartRequest(maintenanceRequestDto);

            } catch (UnreadableException e) {
                e.printStackTrace();
            }
        } else {
            block();
        }
    }

    private void handleStopRequest(MaintenanceRequestDto maintenanceRequestDto) {
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
        System.out.println("Machine stop request for" + machineName + " sent");
    }

    private void handleStartRequest(MaintenanceRequestDto maintenanceRequestDto) {
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
