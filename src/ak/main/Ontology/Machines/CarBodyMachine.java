package ak.main.Ontology.Machines;

import ak.main.Ontology.Constants.MachineResponse;
import ak.main.Ontology.Constants.MachineType;

import java.util.Arrays;

public class CarBodyMachine extends AbstractMachine{
    public CarBodyMachine() {
        responses.addAll(Arrays.asList(
                MachineResponse.ALUMINIUM_LEVEL_LOW,
                MachineResponse.CAR_BODY_BOX_CAPACITY_FULL,
                MachineResponse.LOW_FURNACE_TEMP,
                MachineResponse.MALFUNCTIONING_PRESS,
                MachineResponse.NO_ALUMINIUM,
                MachineResponse.MACHINE_STOPPED
        ));
    }

    public MachineType getMachineType() {
        return MachineType.CAR_BODY_CONSTRUCTION;
    }
}
