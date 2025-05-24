package ak.main.Ontology.Sensors.Dto;

import ak.main.Ontology.Constants.MachineType;
import ak.main.Ontology.Sensors.Constants.SensorTypes;
import jade.content.Concept;

public class SensorAlert implements Concept {
    private MachineType machineType;
    private SensorTypes sensorType;
    private boolean isCritical;

    public MachineType getMachineType() {
        return machineType;
    }

    public SensorAlert setMachineType(MachineType machineType) {
        this.machineType = machineType;
        return this;
    }

    public SensorTypes getSensorType() {
        return sensorType;
    }

    public SensorAlert setSensorType(SensorTypes sensorType) {
        this.sensorType = sensorType;
        return this;
    }

    public boolean isCritical() {
        return isCritical;
    }

    public SensorAlert setCritical(boolean critical) {
        isCritical = critical;
        return this;
    }
}
