package ak.main.Ontology.Machines;

import ak.main.Ontology.Constants.MachineResponse;
import ak.main.Ontology.Constants.MachineType;

import java.util.Arrays;

public class CarPaintMachine extends AbstractMachine {
    public CarPaintMachine() {
        responses.addAll(Arrays.asList(
                MachineResponse.PAINT_NOZZLE_BLOCKED,
                //MachineResponse.NO_PAINT,
                //MachineResponse.PAINT_LEVEL_LOW,
                MachineResponse.MACHINE_STOPPED
        ));
    }

    public MachineType getMachineType() {
        return MachineType.CAR_PAINTING;
    }
}
