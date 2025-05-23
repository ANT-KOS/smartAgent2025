package ak.main.Ontology.Machines;

import ak.main.Ontology.Constants.MachineType;
import ak.main.Ontology.Sensors.SensorThreshold;
import ak.main.Ontology.Sensors.constants.SensorTypes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CncMachine extends Machine {
    private List<SensorThreshold> sensorThresholds = new ArrayList<>();

    public CncMachine() {
        this.sensorThresholds.addAll(Arrays.asList(
                new SensorThreshold(SensorTypes.TEMPERATURE.getSensorType(), 75),
                new SensorThreshold(SensorTypes.VIBRATION.getSensorType(), 0.1, 1.5),
                new SensorThreshold(SensorTypes.SPINDLE_RPM.getSensorType(), 1000, 8000),
                new SensorThreshold(SensorTypes.CURRENT.getSensorType(), 30),
                new SensorThreshold(SensorTypes.COOLANT_FLOW.getSensorType(), 10),
                new SensorThreshold(SensorTypes.POSITION.getSensorType(), 0.1),
                new SensorThreshold(SensorTypes.AIR_PRESSURE.getSensorType(), 5, 8)
        ));
    }

    @Override
    public List<SensorThreshold> getSensorThresholds() {
        return List.of();
    }

    @Override
    public String getMachineType() {
        return MachineType.CNC_MACHINE.getMachineType();
    }
}
