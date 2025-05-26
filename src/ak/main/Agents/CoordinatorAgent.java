package ak.main.Agents;

import ak.main.Behaviours.MachineStartReplyReciever;
import ak.main.Behaviours.MachineStatusInspector;
import ak.main.Behaviours.MachineStopReplyReciever;
import ak.main.Behaviours.MaintenanceResponseReceiver;
import ak.main.Ontology.CarFactoryOntology;
import ak.main.Ontology.Constants.MachineStatus;
import ak.main.Ontology.Constants.MachineType;
import jade.content.lang.sl.SLCodec;
import jade.core.Agent;

import java.util.HashMap;
import java.util.Map;

public class CoordinatorAgent extends Agent {
    private Map<MachineType, MachineStatus> machineStatusPerMachine = new HashMap<>();

    protected void setup() {

        getContentManager().registerLanguage(new SLCodec());
        getContentManager().registerOntology(CarFactoryOntology.ontologyInstance);

        addBehaviour(new MachineStartReplyReciever(this));
        addBehaviour(new MachineStopReplyReciever(this));
        addBehaviour(new MachineStatusInspector(this));
        addBehaviour(new MaintenanceResponseReceiver(this));
    }

    public void changeMachineStatus(MachineType machineType, MachineStatus newStatus) {
        machineStatusPerMachine.computeIfAbsent(machineType, k -> newStatus);
        System.out.println(machineStatusPerMachine);
    }

    public MachineStatus getMachineStatus(MachineType machineType) {
        return machineStatusPerMachine.get(machineType);
    }
}