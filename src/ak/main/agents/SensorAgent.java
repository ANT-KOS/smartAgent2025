import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class SensorAgent extends Agent {

    protected void setup() {
        System.out.println("Sensor Agent " + getAID().getName() + " started.");

        // Adding a cyclic behaviour for continuously checking sensor data
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                // Simulate sensor data reading
                double sensorValue = Math.random() * 100; // Random value between 0 and 100

                System.out.println("Sensor value: " + sensorValue);

                // If the sensor value exceeds a threshold, notify the coordinator
                if (sensorValue > 80) {
                    ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                    msg.addReceiver(getAID("CoordinatorAgent"));
                    msg.setContent("Sensor failure detected! Value: " + sensorValue);
                    send(msg);
                    System.out.println("Failure detected. Alerting CoordinatorAgent...");
                }

                block();  // Wait for the next cycle
            }
        });
    }
}