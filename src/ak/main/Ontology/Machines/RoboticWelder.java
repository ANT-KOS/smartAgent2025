package ak.main.Ontology.Machines;

import ak.main.Ontology.Constants.MachineType;
import ak.main.Ontology.Sensors.Constants.SensorTypes;
import ak.main.Ontology.Sensors.Dto.SensorThreshold;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoboticWelder extends Machine{
    private final List<SensorThreshold> sensorThresholds = new ArrayList<>();

    public RoboticWelder() {
        sensorThresholds.addAll(Arrays.asList(
                new SensorThreshold(SensorTypes.TEMPERATURE.getSensorType(), 1200, 1500),
                new SensorThreshold(SensorTypes.CURRENT.getSensorType(), 100, 300),
                new SensorThreshold(SensorTypes.VOLTAGE.getSensorType(), 20, 35),
                new SensorThreshold(SensorTypes.FLOW.getSensorType(), 10, 25),
                new SensorThreshold(SensorTypes.POSITION.getSensorType(), -2, 2),
                new SensorThreshold(SensorTypes.VIBRATION.getSensorType(), 2),
                new SensorThreshold(SensorTypes.FUME_CONCENTRATION.getSensorType(), 35)
        ));
    }

    @Override
    public List<SensorThreshold> getSensorThresholds() {
        return sensorThresholds;
    }

    @Override
    public String getMachineType() {
        return MachineType.ROBOTIC_WELDER.getMachineType();
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String getStatus() {
        return status;
    }
}
