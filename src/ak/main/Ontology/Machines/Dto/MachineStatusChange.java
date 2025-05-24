package ak.main.Ontology.Machines.Dto;

import ak.main.Ontology.Constants.MachineStatus;
import ak.main.Ontology.Constants.MachineType;
import jade.content.Concept;

public class MachineStatusChange implements Concept {
    private MachineType machineType;
    private MachineStatus machineStatus;

    public MachineType getMachineType() {
        return machineType;
    }

    public MachineStatusChange setMachineType(MachineType machineType) {
        this.machineType = machineType;
        return this;
    }

    public MachineStatus getMachineStatus() {
        return machineStatus;
    }

    public MachineStatusChange setMachineStatus(MachineStatus machineStatus) {
        this.machineStatus = machineStatus;
        return this;
    }
}
