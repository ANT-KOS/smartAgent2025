package ak.main.Ontology.Machines;

import ak.main.Ontology.Constants.MachineType;
import ak.main.Ontology.Sensors.Constants.SensorTypes;
import ak.main.Ontology.Sensors.Dto.SensorThreshold;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AutomatedPainting extends Machine {
    private final List<SensorThreshold> sensorThresholds = new ArrayList<>();

    public AutomatedPainting() {
        sensorThresholds.addAll(Arrays.asList(
                new SensorThreshold(SensorTypes.PRESSURE.getSensorType(), 2, 4),
                new SensorThreshold(SensorTypes.TEMPERATURE.getSensorType(), 20, 30),
                new SensorThreshold(SensorTypes.HUMIDITY.getSensorType(), 40, 60),
                new SensorThreshold(SensorTypes.FLOW.getSensorType(), 100, 300),
                new SensorThreshold(SensorTypes.FUME_CONCENTRATION.getSensorType(), 5)
        ));
    }

    @Override
    public List<SensorThreshold> getSensorThresholds() {
        return sensorThresholds;
    }

    @Override
    public String getMachineType() {
        return MachineType.AUTOMATED_PAINTING.getMachineType();
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
