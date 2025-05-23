package ak.main.Ontology.Machines;

import ak.main.Ontology.Sensors.SensorThreshold;
import ak.main.Ontology.Sensors.constants.SensorTypes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HydraulicPress extends Machine {
    private List<SensorThreshold> sensorThresholds = new ArrayList<>();

    public HydraulicPress() {
        this.sensorThresholds.addAll(Arrays.asList(
                new SensorThreshold(SensorTypes.TEMPERATURE.getSensorType(), 30, 70),
                new SensorThreshold(SensorTypes.PRESSURE.getSensorType(), 100, 250),
                new SensorThreshold(SensorTypes.VIBRATION.getSensorType(), 1.5),
                new SensorThreshold(SensorTypes.POSITION.getSensorType(), -0.1, 0.1),
                new SensorThreshold(SensorTypes.CURRENT.getSensorType(), 15, 40),
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
        return "";
    }
}
