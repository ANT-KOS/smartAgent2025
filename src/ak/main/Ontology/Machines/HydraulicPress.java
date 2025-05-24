package ak.main.Ontology.Machines;

import ak.main.Ontology.Constants.MachineType;
import ak.main.Ontology.Sensors.*;
import ak.main.Ontology.Sensors.Constants.SensorTypes;
import ak.main.Ontology.Sensors.Dto.SensorThreshold;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HydraulicPress extends Machine {
    private final ArrayList<SensorThreshold> sensorThresholds = new ArrayList<>();

    public HydraulicPress() {
        this.sensorThresholds.addAll(Arrays.asList(
                new SensorThreshold(SensorTypes.TEMPERATURE.getSensorType(), 30, 70, 25, 80),
                new SensorThreshold(SensorTypes.PRESSURE.getSensorType(), 100, 250, 90, 280),
                new SensorThreshold(SensorTypes.VIBRATION.getSensorType(), 1.5, null, 2.0),
                new SensorThreshold(SensorTypes.POSITION.getSensorType(), -1, 1, -1.5, 1.5),
                new SensorThreshold(SensorTypes.CURRENT.getSensorType(), 15, 40, 14, 45),
                new SensorThreshold(SensorTypes.FLOW.getSensorType(), 10, 50, 8, 60)
        ));
    }

    @Override
    public ArrayList<AbstractSensor> getSensors() {
        return new ArrayList<>(List.of(
                new TemperatureSensor(),
                new PneymaticSystemSensor(),
                new VibrationSensor(),
                new PositionSensor(),
                new CurrentSensor(),
                new FlowSensor()
        ));
    }

    @Override
    public ArrayList<SensorThreshold> getSensorThresholds() {
        return sensorThresholds;
    }

    @Override
    public MachineType getMachineType() {
        return MachineType.HYDRAULIC_PRESS;
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
