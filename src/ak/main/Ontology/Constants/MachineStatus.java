package ak.main.Ontology.Constants;

public enum MachineStatus {
    OPERATING("operating"),
    UNDER_MAINTENANCE("underMaintenance"),
    FAULTY("faulty"),
    STOPPED("stopped");

    private final String machineStatus;

    MachineStatus(String machineStatus) {
        this.machineStatus = machineStatus;
    }

    public String getMachineStatus() {
        return machineStatus;
    }
}
