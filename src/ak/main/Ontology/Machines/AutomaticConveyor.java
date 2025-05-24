package ak.main.Ontology.Machines;

import ak.main.Ontology.Constants.MachineType;
import ak.main.Ontology.Sensors.*;
import ak.main.Ontology.Sensors.Constants.SensorTypes;
import ak.main.Ontology.Sensors.Dto.SensorThreshold;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AutomaticConveyor extends Machine{
    private final ArrayList<SensorThreshold> sensorThresholds = new ArrayList<>();

    public AutomaticConveyor() {
        sensorThresholds.addAll(Arrays.asList(
                new SensorThreshold(SensorTypes.TEMPERATURE.getSensorType(), 30, 80, 28, 90),
                new SensorThreshold(SensorTypes.VIBRATION.getSensorType(), 1.5, null, 2.0),
                new SensorThreshold(SensorTypes.BELT_ALIGNMENT.getSensorType(), -2, 2, -2.5, 2.5),
                new SensorThreshold(SensorTypes.CURRENT.getSensorType(), 5, 20, 4, 25)
        ));
    }

    @Override
    public ArrayList<AbstractSensor> getSensors() {
         return new ArrayList<>(List.of(
                new TemperatureSensor(),
                new VibrationSensor(),
                new BeltAlignmentSensor(),
                new CurrentSensor()
        ));
    }

    @Override
    public ArrayList<SensorThreshold> getSensorThresholds() {
        return sensorThresholds;
    }

    @Override
    public MachineType getMachineType() {
        return MachineType.AUTOMATIC_CONVEYOR;
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
