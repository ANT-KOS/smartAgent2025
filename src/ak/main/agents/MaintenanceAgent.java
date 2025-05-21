package ak.main.agents;

import ak.main.ontology.Task;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.PriorityQueue;

public class MaintenanceAgent extends Agent {
    private PriorityQueue<Task> maintenanceQueue = new PriorityQueue<>();

    protected void setup() {

        System.out.println("Maintenance Agent " + getAID().getName() + " started.");
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage msg = receive();  // Wait for messages (failure alerts)
                if (msg != null) {
                    // Simulate receiving a failure report with priority
                    String taskDescription = msg.getContent();
                    int priority = (taskDescription.contains("critical")) ? 1 : 10;  // Example logic
                    maintenanceQueue.add(new Task(priority, taskDescription));
                    System.out.println("Task added with priority: " + priority);
                } else {
                    block();  // Wait for the next message
                }
            }
        });

        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                if (!maintenanceQueue.isEmpty()) {
                    Task task = maintenanceQueue.poll();
                    System.out.println("Performing maintenance: " + task.getTaskDescription());
                } else {
                    block();
                }
            }
        });
    }
}