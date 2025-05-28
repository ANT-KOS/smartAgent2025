package ak.main.Ontology.Constants;

public enum MachineStatus {
    OPERATING("operating"),
    UNDER_MAINTENANCE("underMaintenance"),
    FAULTY("faulty"),
    STOPPED("stopped");

    MachineStatus(String machineStatus) {
    }
}
