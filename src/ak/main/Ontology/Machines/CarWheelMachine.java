package ak.main.Ontology.Machines;

import ak.main.Ontology.Constants.MachineResponse;
import ak.main.Ontology.Constants.MachineType;

import java.util.Arrays;

public class CarWheelMachine extends AbstractMachine{
    public CarWheelMachine() {
        responses.addAll(Arrays.asList(
                MachineResponse.NO_PLASTIC,
                MachineResponse.LOW_FURNACE_TEMP,
                MachineResponse.MALFUNCTIONING_PRESS,
                MachineResponse.PLASTIC_LEVEL_LOW,
                MachineResponse.CAR_WHEEL_BOX_CAPACITY_FULL
        ));
    }

    public MachineType getMachineType() {
        return MachineType.CAR_WHEEL_CONSTRUCTION;
    }
}