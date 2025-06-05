package ak.main.Behaviours.Machine;

import ak.main.Agents.Constants.AgentNames;
import ak.main.Ontology.Machines.AbstractMachine;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

//This behaviour handles any stop requests for the machines.
public class MachineStopRequestReciever extends CyclicBehaviour {
    AbstractMachine machine;
    public MachineStopRequestReciever(Agent a, AbstractMachine machine) {
        super(a);
        this.machine = machine;
    }

    public void action() {
        MessageTemplate messageTemplate = MessageTemplate.and(
                MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
                MessageTemplate.MatchSender(new AID(AgentNames.MAINTENANCE_AGENT.getAgentName(), AID.ISLOCALNAME))
        );

        ACLMessage msg = myAgent.receive(messageTemplate);
        if (msg != null) {
            String content = msg.getContent();
            if (
                    content != null
                            && content.startsWith("STOP")
                            && machine.getMachineType().getMachineName().equals(content.substring("STOP".length()).trim())
            ) {
                machine.stop();
                ACLMessage message = new ACLMessage(ACLMessage.INFORM);
                message.setPerformative(ACLMessage.INFORM);
                message.setContent("Machine " + machine.getMachineType().getMachineName() + " stopped");
                myAgent.send(message);
                System.out.println("Machine STOP request to " + machine.getMachineType().getMachineName() + " received");
            }
        } else {
            block();
        }
    }
}
