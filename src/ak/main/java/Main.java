import jade.wrapper.AgentContainer;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.core.Runtime;


public class Main {

    public static void main(String[] args) {
        try {
            // Create a runtime for the JADE platform
            Runtime runtime = Runtime.instance();

            // Create a profile for the container
            Profile profile = new ProfileImpl();
            profile.setParameter(Profile.MAIN_HOST, "localhost");
            profile.setParameter(Profile.MAIN_PORT, "1099");  // Default port, adjust as needed

            // Create the main container
            AgentContainer container = runtime.createMainContainer(profile);

            // Create agents and add them to the container
            AgentController sensorAgent = container.createNewAgent("SensorAgent", "SensorAgent", null);  // No arguments needed
            AgentController maintenanceAgent = container.createNewAgent("MaintenanceAgent", "MaintenanceAgent", null);  // No arguments needed
            AgentController coordinatorAgent = container.createNewAgent("CoordinatorAgent", "CoordinatorAgent", null);  // No arguments needed

            // Start the agents
            sensorAgent.start();
            maintenanceAgent.start();
            coordinatorAgent.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
