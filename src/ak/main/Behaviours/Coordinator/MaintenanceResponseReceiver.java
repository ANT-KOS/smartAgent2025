package ak.main.Behaviours.Coordinator;

import ak.main.Agents.CoordinatorAgent;
import ak.main.Ontology.Constants.MachineStatus;
import ak.main.Ontology.Constants.MachineType;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class MaintenanceResponseReceiver extends CyclicBehaviour {
    public MaintenanceResponseReceiver(Agent agent) {
        super(agent);
    }

    public void action() {
        MessageTemplate template = null;

        for (AID maintenanceAgent : ((CoordinatorAgent) myAgent).getMaintenanceAgents()) {
            MessageTemplate match = MessageTemplate.MatchSender(maintenanceAgent);
            if (template == null) {
                template = match;
            } else {
                template = MessageTemplate.or(template, match);
            }
        }

        if (template != null) {
            template = MessageTemplate.and(template, MessageTemplate.MatchPerformative(ACLMessage.INFORM));

            ACLMessage msg = myAgent.receive(template);

            if (msg != null) {
                if (!msg.getConversationId().isEmpty() &&
                        msg.getConversationId().equals(msg.getReplyWith())) {
                    String response = msg.getContent();
                    MachineType machineType = extractMachineType(response);
                    MachineStatus machineStatus = ((CoordinatorAgent) myAgent).getMachineStatus(machineType);

                    if (response.contains("REPAIRED")) {
                        if (machineStatus == null || !machineStatus.equals(MachineStatus.OPERATING)) {
                            ((CoordinatorAgent) myAgent).changeMachineStatus(machineType, MachineStatus.OPERATING);
                            ((CoordinatorAgent) myAgent).markMaintenanceAgentAsAvailable(msg.getSender());
                        }
                    } else if (response.contains("REPAIRS STARTED")) {
                        if (machineStatus == null || !machineStatus.equals(MachineStatus.UNDER_MAINTENANCE)) {
                            ((CoordinatorAgent) myAgent).changeMachineStatus(machineType, MachineStatus.UNDER_MAINTENANCE);
                        }
                    } else if (response.contains("STOPPED")) {
                        if (machineStatus == null || !machineStatus.equals(MachineStatus.STOPPED)) {
                            ((CoordinatorAgent) myAgent).changeMachineStatus(machineType, MachineStatus.STOPPED);
                        }
                    }
                }
            } else {
                block();
            }
        } else {
            block();
        }
    }

    private MachineType extractMachineType(String response) {
        String[] parts = response.trim().split("\\s+");
        String lastWord = parts[parts.length - 1];
        lastWord = lastWord.replaceAll("[^a-zA-Z0-9]", "");
        return MachineType.fromValue(lastWord);
    }
}
