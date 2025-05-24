package ak.main.Ontology.Machines;

import ak.main.Ontology.Constants.MachineType;
import ak.main.Ontology.Sensors.*;
import ak.main.Ontology.Sensors.Constants.SensorTypes;
import ak.main.Ontology.Sensors.Dto.SensorThreshold;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AutomatedPainting extends Machine {
    private final ArrayList<SensorThreshold> sensorThresholds = new ArrayList<>();

    public AutomatedPainting() {
        sensorThresholds.addAll(Arrays.asList(
                new SensorThreshold(SensorTypes.PRESSURE.getSensorType(), 2, 4, 1, 5),
                new SensorThreshold(SensorTypes.TEMPERATURE.getSensorType(), 20, 30, 15, 35),
                new SensorThreshold(SensorTypes.HUMIDITY.getSensorType(), 40, 60, 35, 65),
                new SensorThreshold(SensorTypes.FLOW.getSensorType(), 100, 300, 90, 310),
                new SensorThreshold(SensorTypes.FUME_CONCENTRATION.getSensorType(), 5, null, 6.0)
        ));
    }

    @Override
    public ArrayList<AbstractSensor> getSensors() {
        return new ArrayList<>(List.of(
                new PressureSensor(),
                new TemperatureSensor(),
                new HumiditySensor(),
                new SprayGunFlowSensor(),
                new FumeDetectionSensor()
        ));
    }

    @Override
    public ArrayList<SensorThreshold> getSensorThresholds() {
        return sensorThresholds;
    }

    @Override
    public MachineType getMachineType() {
        return MachineType.AUTOMATED_PAINTING;
    }

    @Override
    public Machine setStatus(String status) {
        this.status = status;
        return null;
    }

    @Override
    public String getStatus() {
        return status;
    }
}
