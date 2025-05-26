import ak.main.Agents.Constants.AgentNames;
import ak.main.Agents.CoordinatorAgent;
import ak.main.Agents.MachineAgent;
import ak.main.Agents.MaintenanceAgent;
import ak.main.Agents.WarehouseAgent;
import ak.main.Ontology.Machines.*;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;


public class Main {
    private static final Class<?>[] machineClasses = {
            CarAssemblyMachine.class,
            CarBodyMachine.class,
            CarInteriorChassisMachine.class,
            CarPackagingMachine.class,
            CarPaintMachine.class,
            CarWheelMachine.class,
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
                String agentName = AgentNames.MACHINE_AGENT.getAgentName(machine.getMachineType().getMachineName());

                container.createNewAgent(agentName, MachineAgent.class.getName(), new Object[]{machine}).start();
            }

            container.createNewAgent(AgentNames.MAINTENANCE_AGENT.getAgentName(), MaintenanceAgent.class.getName(), null).start();
            container.createNewAgent(AgentNames.COORDINATOR_AGENT.getAgentName(), CoordinatorAgent.class.getName(), null).start();
            container.createNewAgent(AgentNames.WAREHOUSE_AGENT.getAgentName(), WarehouseAgent.class.getName(), null).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
