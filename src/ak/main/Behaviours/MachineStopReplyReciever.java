package ak.main.Behaviours;

import ak.main.Agents.CoordinatorAgent;
import ak.main.Ontology.Constants.MachineStatus;
import ak.main.Ontology.Constants.MachineType;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MachineStopReplyReciever extends CyclicBehaviour {
    public MachineStopReplyReciever(CoordinatorAgent agent) {
        super(agent);
    }

    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);

        ACLMessage msg = myAgent.receive(mt);
        if (msg != null) {
            Pattern pattern = Pattern.compile("^Machine .* stopped\\.$");
            Matcher matcher = pattern.matcher(msg.getContent());
            if (matcher.matches()) {
                String machineName = extractMachineName(msg.getContent());
                MachineType machineType = MachineType.fromValue(machineName);
                ((CoordinatorAgent) myAgent).changeMachineStatus(machineType, MachineStatus.STOPPED);
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
