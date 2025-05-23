package ak.main.Ontology.Constants;

public enum MachineType {
    CNC_MACHINE("cncMachine"),
    HYDRAULIC_PRESS("hydraulicPress"),
    AUTOMATED_CONVEYOR("automatedConveyor"),
    ROBOTIC_WELDING("roboticWelding"),
    AUTOMATED_PAINTING("automatedPainting"),
    QUALITY_INSPECTION("qualityInspection");

    private final String machineType;

    MachineType(String machineType) {
        this.machineType = machineType;
    }

    public String getMachineType() {
        return machineType;
    }
}
