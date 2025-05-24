package ak.main.Ontology.Machines;

import ak.main.Ontology.Constants.MachineType;
import ak.main.Ontology.Sensors.*;
import ak.main.Ontology.Sensors.Constants.SensorTypes;
import ak.main.Ontology.Sensors.Dto.SensorThreshold;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoboticWelder extends Machine{
    private final ArrayList<SensorThreshold> sensorThresholds = new ArrayList<>();

    public RoboticWelder() {
        sensorThresholds.addAll(Arrays.asList(
                new SensorThreshold(SensorTypes.TEMPERATURE.getSensorType(), 1200, 1500, 1190, 1510),
                new SensorThreshold(SensorTypes.CURRENT.getSensorType(), 100, 300, 95, 305),
                new SensorThreshold(SensorTypes.VOLTAGE.getSensorType(), 20, 35, 15, 40),
                new SensorThreshold(SensorTypes.FLOW.getSensorType(), 10, 25, 8, 30),
                new SensorThreshold(SensorTypes.POSITION.getSensorType(), -2, 2, -2.5, 2.5),
                new SensorThreshold(SensorTypes.VIBRATION.getSensorType(), 2, null, 3.0),
                new SensorThreshold(SensorTypes.FUME_CONCENTRATION.getSensorType(), 35, null,  75.0)
        ));
    }

    @Override
    public ArrayList<AbstractSensor> getSensors() {
        return new ArrayList<>(List.of(
                new WeldingTemperatureSensor(),
                new CurrentSensor(),
                new VoltageSensor(),
                new GasFlowSensor(),
                new PositionSensor(),
                new VibrationSensor(),
                new FumeDetectionSensor()
        ));
    }

    @Override
    public ArrayList<SensorThreshold> getSensorThresholds() {
        return sensorThresholds;
    }

    @Override
    public MachineType getMachineType() {
        return MachineType.ROBOTIC_WELDER;
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
