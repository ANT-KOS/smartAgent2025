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
import jade.core.behaviours.ParallelBehaviour;

public class MachineAgent extends Agent {
    @Override
    protected void setup() {
        AID coordinatorAgent = new AID(AgentNames.COORDINATOR_AGENT.getAgentName(), AID.ISLOCALNAME);
        getContentManager().registerLanguage(new SLCodec());
        getContentManager().registerOntology(CarFactoryOntology.ontologyInstance);

        Object[] args = getArguments();
        if (args != null && args.length > 0 && args[0] instanceof AbstractMachine machine) {
            ParallelBehaviour parallelBehaviour = new ParallelBehaviour();


            parallelBehaviour.addSubBehaviour(new MachineStatusSender(this, machine, coordinatorAgent));
            parallelBehaviour.addSubBehaviour(new MachineStartRequestReciever(this, machine));
            parallelBehaviour.addSubBehaviour(new MachineStopRequestReciever(this, machine));

            addBehaviour(parallelBehaviour);
        } else {
            System.out.println("No argument supplied to " + this.getAID());
            doDelete();
        }
    }
}