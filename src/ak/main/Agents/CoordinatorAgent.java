package ak.main.Agents;

import ak.main.Behaviours.Coordinator.MachineStartReplyReciever;
import ak.main.Behaviours.Coordinator.MachineStatusInspector;
import ak.main.Behaviours.Coordinator.MachineStopReplyReciever;
import ak.main.Behaviours.Coordinator.MaintenanceResponseReceiver;
import ak.main.Ontology.CarFactoryOntology;
import ak.main.Ontology.Constants.MachineStatus;
import ak.main.Ontology.Constants.MachineType;
import jade.content.lang.sl.SLCodec;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.ParallelBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

import java.util.*;

public class CoordinatorAgent extends Agent {
    private Map<MachineType, MachineStatus> machineStatusPerMachine = new HashMap<>();
    private List<AID> unavailableMaintenanceAgents = new ArrayList<>();
    private List<AID> maintenanceAgents;

    protected void setup() {

        getContentManager().registerLanguage(new SLCodec());
        getContentManager().registerOntology(CarFactoryOntology.ontologyInstance);
        maintenanceAgents = Arrays.asList(queryMaintenanceAgents());

        ParallelBehaviour parallelBehaviour = new ParallelBehaviour(this, ParallelBehaviour.WHEN_ALL);

        parallelBehaviour.addSubBehaviour(new MachineStartReplyReciever(this));
        parallelBehaviour.addSubBehaviour(new MachineStopReplyReciever(this));
        parallelBehaviour.addSubBehaviour(new MachineStatusInspector(this));
        parallelBehaviour.addSubBehaviour(new MaintenanceResponseReceiver(this));

        addBehaviour(parallelBehaviour);
    }

    public void changeMachineStatus(MachineType machineType, MachineStatus newStatus) {
        machineStatusPerMachine.compute(machineType, (k, v) -> newStatus);
        System.out.println(machineStatusPerMachine);
    }

    public MachineStatus getMachineStatus(MachineType machineType) {
        return machineStatusPerMachine.get(machineType);
    }

    private AID[] queryMaintenanceAgents() {
        DFAgentDescription template = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType("maintenance");
        template.addServices(sd);

        try {
            DFAgentDescription[] result = DFService.search(this, template);
            return Arrays.stream(result)
                    .map(DFAgentDescription::getName)
                    .filter(aid -> aid.getLocalName().startsWith("MaintenanceAgent_Maintenance-"))
                    .toArray(AID[]::new);
        } catch (FIPAException fe) {
            fe.printStackTrace();
            return new AID[0];
        }
    }

    public void markMaintenanceAgentAsUnavailable(AID maintenanceAgent) {
        unavailableMaintenanceAgents.add(maintenanceAgent);
        System.out.println("Maintenance agent " + maintenanceAgent.getLocalName() + " is now unavailable.");
    }

    public void markMaintenanceAgentAsAvailable(AID maintenanceAgent) {
        unavailableMaintenanceAgents.remove(maintenanceAgent);
        System.out.println("Maintenance agent " + maintenanceAgent.getLocalName() + " is now available.");
    }

    public List<AID> getAvailableMaintenanceAgents() {
        ArrayList<AID> availableMaintenanceAgents = new ArrayList<>(maintenanceAgents);
        availableMaintenanceAgents.removeAll(unavailableMaintenanceAgents);
        return availableMaintenanceAgents;
    }

    public List<AID> getMaintenanceAgents() {
        return maintenanceAgents;
    }

    public void notifyRepairCompleted(AID agent) {
        markMaintenanceAgentAsAvailable(agent);
    }
}