package ak.main.Agents;

import ak.main.Ontology.Machines.Machine;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;

import java.util.Random;

public class SensorAgent extends Agent {
    private Machine machine;
    private AID coordinatorAgent;

    public SensorAgent(Machine machine, AID coordinatorAgent) {
        this.machine = machine;
        this.coordinatorAgent = coordinatorAgent;
    }

    protected void setup() {
        System.out.println("Agent " + getLocalName() + " is starting");

        addBehaviour(new TickerBehaviour(this, 1000) {
            @Override
            protected void onTick() {

            }
        });
    }

    private double getSensorReading() {
        // Randomly simulate a sensor reading (for demonstration purposes)
        Random rand = new Random();
        return rand.nextDouble() * 100;  // Return a value between 0 and 100
    }
}