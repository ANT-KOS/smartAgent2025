package ak.main.Agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;

public class MaintenanceAgent extends Agent {

    protected void setup() {

        System.out.println("Maintenance Agent " + getAID().getName() + " started.");
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {

            }
        });

        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {

            }
        });
    }
}