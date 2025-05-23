package ak.main.Ontology.Machines;

import ak.main.Ontology.Constants.MachineType;
import ak.main.Ontology.Sensors.*;
import ak.main.Ontology.Sensors.Constants.SensorTypes;
import ak.main.Ontology.Sensors.Dto.SensorThreshold;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HydraulicPress extends Machine {
    private final List<SensorThreshold> sensorThresholds = new ArrayList<>();

    public HydraulicPress() {
        this.sensorThresholds.addAll(Arrays.asList(
                new SensorThreshold(SensorTypes.TEMPERATURE.getSensorType(), 30, 70),
                new SensorThreshold(SensorTypes.PRESSURE.getSensorType(), 100, 250),
                new SensorThreshold(SensorTypes.VIBRATION.getSensorType(), 1.5),
                new SensorThreshold(SensorTypes.POSITION.getSensorType(), -1, 1),
                new SensorThreshold(SensorTypes.CURRENT.getSensorType(), 15, 40),
                new SensorThreshold(SensorTypes.FLOW.getSensorType(), 10, 50)
        ));
    }

    @Override
    public List<AbstractSensor> getSensors() {
        return List.of(
                new TemperatureSensor(),
                new PneymaticSystemSensor(),
                new VibrationSensor(),
                new PositionSensor(),
                new CurrentSensor(),
                new FlowSensor()
        );
    }

    @Override
    public List<SensorThreshold> getSensorThresholds() {
        return sensorThresholds;
    }

    @Override
    public String getMachineType() {
        return MachineType.HYDRAULIC_PRESS.getMachineType();
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
