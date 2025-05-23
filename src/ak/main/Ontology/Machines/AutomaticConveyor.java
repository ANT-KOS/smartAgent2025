package ak.main.Ontology.Machines;

import ak.main.Ontology.Constants.MachineType;
import ak.main.Ontology.Sensors.*;
import ak.main.Ontology.Sensors.Constants.SensorTypes;
import ak.main.Ontology.Sensors.Dto.SensorThreshold;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AutomaticConveyor extends Machine{
    private final List<SensorThreshold> sensorThresholds = new ArrayList<>();

    public AutomaticConveyor() {
        sensorThresholds.addAll(Arrays.asList(
                new SensorThreshold(SensorTypes.TEMPERATURE.getSensorType(), 30, 80),
                new SensorThreshold(SensorTypes.VIBRATION.getSensorType(), 1.5),
                new SensorThreshold(SensorTypes.BELT_ALIGNMENT.getSensorType(), -2, 2),
                new SensorThreshold(SensorTypes.CURRENT.getSensorType(), 5, 20)
        ));
    }

    @Override
    public List<AbstractSensor> getSensors() {
        return List.of(
                new TemperatureSensor(),
                new VibrationSensor(),
                new BeltAlignmentSensor(),
                new CurrentSensor()
        );
    }

    @Override
    public List<SensorThreshold> getSensorThresholds() {
        return sensorThresholds;
    }

    @Override
    public String getMachineType() {
        return MachineType.AUTOMATIC_CONVEYOR.getMachineType();
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
