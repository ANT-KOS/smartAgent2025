package ak.main.Ontology.Machines;

import ak.main.Ontology.Constants.MachineType;
import ak.main.Ontology.Sensors.*;
import ak.main.Ontology.Sensors.Constants.SensorTypes;
import ak.main.Ontology.Sensors.Dto.SensorThreshold;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CncMachine extends Machine {
    private final ArrayList<SensorThreshold> sensorThresholds = new ArrayList<>();

    public CncMachine() {
        this.sensorThresholds.addAll(Arrays.asList(
                new SensorThreshold(SensorTypes.TEMPERATURE.getSensorType(), 75, null, 85.0),
                new SensorThreshold(SensorTypes.VIBRATION.getSensorType(), 1.5, null, 2.5),
                new SensorThreshold(SensorTypes.SPINDLE_RPM.getSensorType(), 1000, 8000, 500, 9000),
                new SensorThreshold(SensorTypes.CURRENT.getSensorType(), 30, null, 35.0),
                new SensorThreshold(SensorTypes.FLOW.getSensorType(), 10, 8.0, null),
                new SensorThreshold(SensorTypes.POSITION.getSensorType(), 0.1, null, 0.15),
                new SensorThreshold(SensorTypes.PRESSURE.getSensorType(), 5, 8, 4, 9)
        ));
    }

    @Override
    public ArrayList<AbstractSensor> getSensors() {
        return new ArrayList<>(List.of(
                new TemperatureSensor(),
                new VibrationSensor(),
                new SpindleSensor(),
                new CurrentSensor(),
                new FlowSensor(),
                new PositionSensor(),
                new PressureSensor()
        ));
    }

    @Override
    public ArrayList<SensorThreshold> getSensorThresholds() {
        return sensorThresholds;
    }

    @Override
    public MachineType getMachineType() {
        return MachineType.CNC_MACHINE;
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
