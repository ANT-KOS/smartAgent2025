package ak.main.Ontology.Dto;

import ak.main.Ontology.Constants.MachineResponse;
import ak.main.Ontology.Constants.MachineType;
import jade.content.Concept;

public class MaintenanceRequestDto implements Concept {
    private MachineType machineType;
    private MachineResponse machineResponse;

    public MachineType getMachineType() {
        return machineType;
    }

    public MaintenanceRequestDto setMachineType(MachineType machineType) {
        this.machineType = machineType;
        return this;
    }

    public MachineResponse getMachineResponses() {
        return machineResponse;
    }

    public MaintenanceRequestDto setMachineResponse(MachineResponse machineResponse) {
        this.machineResponse = machineResponse;
        return this;
    }
}
