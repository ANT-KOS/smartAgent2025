package ak.main.Ontology.Machines;

import ak.main.Ontology.Constants.MachineResponse;
import ak.main.Ontology.Constants.MachineType;

import java.util.Arrays;

public class CarAssemblyMachine extends AbstractMachine{
    public CarAssemblyMachine() {
        responses.addAll(Arrays.asList(
                MachineResponse.CAR_WHEEL_BOX_CAPACITY_EMPTY,
                MachineResponse.CAR_INTERIOR_CHASSIS_BOX_CAPACITY_EMPTY,
                MachineResponse.CAR_BODY_BOX_CAPACITY_EMPTY,
                MachineResponse.SERVO_MALFUNCTION,
                MachineResponse.MACHINE_STOPPED
        ));
    }

    public MachineType getMachineType() {
        return MachineType.CAR_ASSEMBLY;
    }
}
