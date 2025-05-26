package ak.main.Ontology.Constants;

import ak.main.Ontology.Machines.*;

public enum MachineType {
    CAR_BODY_CONSTRUCTION("carBodyConstruction"),
    CAR_WHEEL_CONSTRUCTION("carWheelConstruction"),
    CAR_INTERIOR_CHASSIS_CONSTRUCTION("carInteriorChassisConstruction"),
    CAR_PAINTING("carPainting"),
    CAR_PACKAGING("carPackaging"),
    CAR_ASSEMBLY("carAssembly");

    private final String machineName;

    MachineType(String machineName) {
        this.machineName = machineName;
    }

    public String getMachineName() {
        return machineName;
    }

    public AbstractMachine getMachine() {
        return switch (this) {
            case MachineType.CAR_BODY_CONSTRUCTION -> new CarBodyMachine();
            case MachineType.CAR_WHEEL_CONSTRUCTION -> new CarWheelMachine();
            case MachineType.CAR_INTERIOR_CHASSIS_CONSTRUCTION -> new CarInteriorChassisMachine();
            case MachineType.CAR_PAINTING -> new CarPaintMachine();
            case MachineType.CAR_ASSEMBLY -> new CarAssemblyMachine();
            case MachineType.CAR_PACKAGING -> new CarPackagingMachine();
        };
    }

    public static MachineType fromValue(String value) {
        for (MachineType type : MachineType.values()) {
            if (type.machineName.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}
