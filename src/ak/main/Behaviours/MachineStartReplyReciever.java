package ak.main.Behaviours;

import ak.main.Agents.Constants.AgentNames;
import ak.main.Agents.CoordinatorAgent;
import ak.main.Ontology.Constants.MachineStatus;
import ak.main.Ontology.Constants.MachineType;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class MachineStartReplyReciever extends CyclicBehaviour {
    public MachineStartReplyReciever(CoordinatorAgent agent) {
        super(agent);
    }

    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.and(
                MessageTemplate.MatchPerformative(ACLMessage.INFORM),
                MessageTemplate.MatchSender(new AID(AgentNames.COORDINATOR_AGENT.getAgentName(), AID.ISLOCALNAME))
        );

        ACLMessage msg = myAgent.receive(mt);
        if (msg != null) {
            if (msg.getContent().contains("START")) {
                String machineName = extractMachineName(msg.getContent());
                MachineType machineType = MachineType.fromValue(machineName);
                ((CoordinatorAgent) myAgent).changeMachineStatus(machineType, MachineStatus.OPERATING);
                System.out.println("Machine " + machineType + " has started");
            }
        } else {
            block();
        }
    }

    private String extractMachineName(String content) {
        String[] parts = content.split(" "); // Split by space
        if (parts.length > 1) {
            return parts[1];
        } else {
            return null;
        }
    }
}
