package ak.main.agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class MaintenanceAgent extends Agent {

    protected void setup() {
        System.out.println("Maintenance Agent " + getAID().getName() + " started.");
        // Adding a cyclic behaviour to listen for failure reports
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage msg = receive();  // Wait for messages (failure alerts)
                if (msg != null) {
                    System.out.println("Received message: " + msg.getContent());

                    // Simulate maintenance action
                    System.out.println("Performing maintenance on the sensor...");

                    // Send confirmation message back to coordinator
                    ACLMessage reply = msg.createReply();
                    reply.setContent("Maintenance completed on the sensor.");
                    send(reply);
                } else {
                    block();  // Wait for the next message
                }
            }
        });
    }
}