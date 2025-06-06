package ak.main.Behaviours.Coordinator;

import ak.main.Agents.CoordinatorAgent;
import ak.main.Ontology.Dto.MaintenanceRequestDto;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;

import java.io.IOException;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MaintenanceRetryManager {
    private static final Queue<MaintenanceRequestDto> retryQueue = new ConcurrentLinkedQueue<>();

    // Add request to retry queue
    public static void queue(MaintenanceRequestDto request) {
        retryQueue.offer(request);
        System.out.println("[MaintenanceRetry] Request queued for retry: " + request.getMachineType());
    }

    // Start retry loop, processing one request at a time every 5 seconds
    public static void startRetryLoop(Agent agent) {
        agent.addBehaviour(new TickerBehaviour(agent, 5000) { // Retry every 5 seconds
            @Override
            protected void onTick() {
                if (retryQueue.isEmpty()) return;

                // Get available maintenance agents
                CoordinatorAgent coordinator = (CoordinatorAgent) agent;
                List<AID> availableAgents = coordinator.getAvailableMaintenanceAgents();

                if (availableAgents == null || availableAgents.isEmpty()) {
                    System.out.println("No available agents during retry. Will try again.");
                    return;
                }

                // Retry all pending requests
                System.out.println("Retrying " + retryQueue.size() + " queued request(s)");

                // Process all queued maintenance requests
                MaintenanceRequestDto dto;
                while ((dto = retryQueue.poll()) != null) {
                    try {
                        new MachineStatusInspector(coordinator).handleMaintenanceRequest(dto.getMachineType(), dto.getMachineResponse());
                    } catch (IOException e) {
                        System.err.println("Failed to retry maintenance request: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
