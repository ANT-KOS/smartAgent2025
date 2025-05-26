package ak.main.Behaviours;

import ak.main.Agents.Constants.AgentNames;
import ak.main.Ontology.Machines.AbstractMachine;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class MachineStartRequestReciever extends CyclicBehaviour {
    AbstractMachine machine;
    public MachineStartRequestReciever(Agent a, AbstractMachine machine) {
        super(a);
        this.machine = machine;
    }

    public void action() {
        MessageTemplate messageTemplate = MessageTemplate.and(
                MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
                MessageTemplate.MatchSender(new AID(AgentNames.COORDINATOR_AGENT.getAgentName(), AID.ISLOCALNAME))
        );

        ACLMessage msg = myAgent.receive(messageTemplate);
        if (msg != null) {
            String content = msg.getContent();
            if (
                    content != null
                            && content.startsWith("START")
                            && machine.getMachineType().getMachineName().equals(content.substring("START".length()).trim())
            ) {
                machine.start();
                ACLMessage reply = msg.createReply();
                reply.setPerformative(ACLMessage.INFORM);
                reply.setContent("Machine " + machine.getMachineType().getMachineName() + " started");
                myAgent.send(reply);
            }
        } else {
            block();
        }
    }
}
