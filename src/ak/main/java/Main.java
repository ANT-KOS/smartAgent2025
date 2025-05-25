import ak.main.Agents.CoordinatorAgent;
import ak.main.Agents.MachineAgent;
import ak.main.Agents.MaintenanceAgent;
import ak.main.Ontology.Machines.AbstractMachine;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;


public class Main {
    private static final Class<?>[] machineClasses = {
            AutomatedPainting.class,
            AutomaticConveyor.class,
            CncMachine.class,
            HydraulicPress.class,
            RoboticWelder.class,
    };

    public static void main(String[] args) {
        try {
            Runtime runtime = Runtime.instance();
            runtime.setCloseVM(true);

            Profile profile = new ProfileImpl();
            profile.setParameter(Profile.MAIN_HOST, "localhost");
            profile.setParameter(Profile.MAIN_PORT, "1099");
            profile.setParameter(Profile.GUI, "true");

            AgentContainer container = runtime.createMainContainer(profile);

            for (Class<?> machineClass : machineClasses) {
                AbstractMachine machine = (AbstractMachine) machineClass.newInstance();
                String agentName = "SensorAgent_" + machine.getMachineType().getMachineType();

                container.createNewAgent(agentName, MachineAgent.class.getName(), new Object[]{machine}).start();
            }

            container.createNewAgent("MaintenanceAgent", MaintenanceAgent.class.getName(), null).start();
            container.createNewAgent("CoordinatorAgent", CoordinatorAgent.class.getName(), null).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
