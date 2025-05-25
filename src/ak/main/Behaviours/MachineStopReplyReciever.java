package ak.main.Behaviours;

import ak.main.Agents.CoordinatorAgent;
import ak.main.Ontology.Constants.MachineStatus;
import ak.main.Ontology.Constants.MachineType;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class MachineStopReplyReciever extends CyclicBehaviour {
    public MachineStopReplyReciever(CoordinatorAgent agent) {
        super(agent);
    }

    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.and(
                MessageTemplate.MatchPerformative(ACLMessage.INFORM),
                MessageTemplate.MatchContent("^Machine .* stopped\\.$")
        );

        ACLMessage msg = myAgent.receive(mt);
        if (msg != null) {
            String machineName = extractMachineName(msg.getContent());
            MachineType machineType = MachineType.valueOf(machineName);
            ((CoordinatorAgent) myAgent).changeMachineStatus(machineType, MachineStatus.STOPPED);
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
