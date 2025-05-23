package ak.main.Ontology.Constants;

public enum MachineType {
    CNC_MACHINE("cncMachine"),
    HYDRAULIC_PRESS("hydraulicPress"),
    AUTOMATIC_CONVEYOR("automaticConveyor"),
    ROBOTIC_WELDER("roboticWelder"),
    AUTOMATED_PAINTING("automatedPainting");

    private final String machineType;

    MachineType(String machineType) {
        this.machineType = machineType;
    }

    public String getMachineType() {
        return machineType;
    }
}
