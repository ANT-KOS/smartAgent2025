package ak.main.Behaviours;

import ak.main.Agents.Constants.AgentNames;
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
        MessageTemplate template = MessageTemplate.and(
                MessageTemplate.MatchSender(new AID(AgentNames.MAINTENANCE_AGENT.getAgentName(), AID.ISLOCALNAME)),
                MessageTemplate.MatchPerformative(ACLMessage.INFORM)
        );

        ACLMessage msg = myAgent.receive(template);
        if (msg != null) {
            if (!msg.getConversationId().isEmpty() &&
                    msg.getConversationId().equals(msg.getReplyWith())) {
                String response = msg.getContent();
                MachineType machineType = extractMachineType(response);
                MachineStatus machineStatus = ((CoordinatorAgent) myAgent).getMachineStatus(machineType);

                if (response.contains("REPAIRED")) {
                    if (!machineStatus.equals(MachineStatus.OPERATING)) {
                        ((CoordinatorAgent) myAgent).changeMachineStatus(machineType, MachineStatus.OPERATING);
                    }
                } else if (response.contains("REPAIRS STARTED")) {
                    if (!machineStatus.equals(MachineStatus.UNDER_MAINTENANCE)) {
                        ((CoordinatorAgent) myAgent).changeMachineStatus(machineType, MachineStatus.UNDER_MAINTENANCE);
                    }
                } else if (response.contains("STOPPED")) {
                    if (!machineStatus.equals(MachineStatus.STOPPED)) {
                        ((CoordinatorAgent) myAgent).changeMachineStatus(machineType, MachineStatus.STOPPED);
                    }
                }
            }
        } else {
            block();
        }
    }

    private MachineType extractMachineType(String response) {
        return MachineType.valueOf(response.split(" ")[3].toUpperCase());
    }
}
