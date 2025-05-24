package ak.main.Ontology.Constants;

import ak.main.Ontology.Machines.*;

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

    public Machine getMachine(MachineType machineType) {
        return switch (machineType) {
            case MachineType.CNC_MACHINE -> new CncMachine();
            case MachineType.AUTOMATED_PAINTING -> new AutomatedPainting();
            case MachineType.AUTOMATIC_CONVEYOR -> new AutomaticConveyor();
            case MachineType.HYDRAULIC_PRESS -> new HydraulicPress();
            case MachineType.ROBOTIC_WELDER -> new RoboticWelder();
        };
    }
}
