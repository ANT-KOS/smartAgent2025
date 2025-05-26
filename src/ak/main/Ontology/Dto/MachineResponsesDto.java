package ak.main.Ontology.Dto;

import ak.main.Ontology.Constants.MachineResponse;
import ak.main.Ontology.Constants.MachineType;
import jade.content.Concept;

import java.util.ArrayList;

public class MachineResponsesDto implements Concept {
    private MachineType machineType;
    private ArrayList<MachineResponse> machineResponse;

    public MachineType getMachineType() {
        return machineType;
    }

    public MachineResponsesDto setMachineType(MachineType machineType) {
        this.machineType = machineType;
        return this;
    }

    public ArrayList<MachineResponse> getMachineResponses() {
        return machineResponse;
    }

    public MachineResponsesDto setMachineResponses(ArrayList<MachineResponse> machineRespons) {
        this.machineResponse = machineRespons;
        return this;
    }
}
