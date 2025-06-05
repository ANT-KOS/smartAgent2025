package ak.main.Agents;

import ak.main.Agents.Constants.AgentNames;
import ak.main.Behaviours.Machine.MachineStartRequestReciever;
import ak.main.Behaviours.Machine.MachineStatusSender;
import ak.main.Behaviours.Machine.MachineStopRequestReciever;
import ak.main.Ontology.CarFactoryOntology;
import ak.main.Ontology.Machines.AbstractMachine;
import jade.content.lang.sl.SLCodec;
import jade.core.AID;
import jade.core.Agent;

import java.util.Random;

public class MachineAgent extends Agent {
    @Override
    protected void setup() {
        AID coordinatorAgent = new AID(AgentNames.COORDINATOR_AGENT.getAgentName(), AID.ISLOCALNAME);
        getContentManager().registerLanguage(new SLCodec());
        getContentManager().registerOntology(CarFactoryOntology.ontologyInstance);

        Object[] args = getArguments();
        if (args != null && args.length > 0 && args[0] instanceof AbstractMachine machine) {
            Random random = new Random();

            addBehaviour(new MachineStatusSender(this, machine, coordinatorAgent, 14000 + (random.nextInt(6) * 1000)));
            addBehaviour(new MachineStartRequestReciever(this, machine));
            addBehaviour(new MachineStopRequestReciever(this, machine));
        } else {
            System.out.println("No argument supplied to " + this.getAID());
            doDelete();
        }
    }
}