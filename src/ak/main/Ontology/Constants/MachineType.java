package ak.main.Ontology.Constants;

public enum MachineType {
    CAR_BODY_CONSTRUCTION("carBodyConstruction"),
    CAR_WHEEL_CONSTRUCTION("carWheelConstruction"),
    CAR_INTERIOR_CHASSIS_CONSTRUCTION("carInteriorChassisConstruction"),
    CAR_PAINTING("carPainting"),
    CAR_PACKAGING("carPackaging"),
    CAR_PARTS_MONITORING("carPartsMonitoring"),
    CAR_ASSEMBLY("carAssembly");

    private final String machineType;

    MachineType(String machineType) {
        this.machineType = machineType;
    }

    public String getMachineType() {
        return machineType;
    }
}
