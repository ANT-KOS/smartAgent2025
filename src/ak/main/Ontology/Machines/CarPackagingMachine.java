package ak.main.Ontology.Machines;

import ak.main.Ontology.Constants.MachineResponse;
import ak.main.Ontology.Constants.MachineType;

import java.util.Arrays;

public class CarPackagingMachine extends AbstractMachine {
    public CarPackagingMachine() {
        responses.addAll(Arrays.asList(
                MachineResponse.PACKAGING_MATERIAL_EMPTY,
                MachineResponse.PACKAGING_MATERIAL_LOW,
                MachineResponse.PACKAGING_JAM,
                MachineResponse.MACHINE_STOPPED
        ));
    }

    public MachineType getMachineType() {
        return MachineType.CAR_PACKAGING;
    }
}
