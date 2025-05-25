package ak.main.Behaviours;

import ak.main.Agents.Constants.AgentNames;
import ak.main.Ontology.Machines.AbstractMachine;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class MachineStartRequestSender extends CyclicBehaviour {
    private AbstractMachine machine;
    private boolean sent = false;

    public MachineStartRequestSender(Agent agent, AbstractMachine machine) {
        super(agent);
        this.machine = machine;
    }

    public void action() {
        if (!sent) {
            String machineName = machine.getMachineType().getMachineType();

            ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
            request.addReceiver(new AID(AgentNames.MACHINE_AGENT.getAgentName(machineName), AID.ISLOCALNAME));
            request.setContent("START " + machineName);
            request.setConversationId("machineStart_" + machineName + "_" + System.currentTimeMillis());
            myAgent.send(request);
            sent = true;
        } else {
            block();
        }
    }
}
