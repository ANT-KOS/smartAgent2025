import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class CoordinatorAgent extends Agent {

    protected void setup() {
        System.out.println("Coordinator Agent " + getAID().getName() + " started.");

        // Adding a cyclic behaviour to coordinate the maintenance process
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage msg = receive();  // Wait for failure reports from SensorAgent
                if (msg != null) {
                    System.out.println("Received failure report: " + msg.getContent());

                    // Notify MaintenanceAgent to perform maintenance
                    ACLMessage maintenanceRequest = new ACLMessage(ACLMessage.REQUEST);
                    maintenanceRequest.addReceiver(getAID("MaintenanceAgent"));
                    maintenanceRequest.setContent("Please fix the sensor.");
                    send(maintenanceRequest);

                    // Wait for maintenance confirmation
                    ACLMessage maintenanceResponse = blockingReceive();
                    if (maintenanceResponse != null) {
                        System.out.println("Maintenance response: " + maintenanceResponse.getContent());
                    }
                } else {
                    block();  // Wait for the next message
                }
            }
        });
    }
}