package ak.main.Ontology.Machines;

import ak.main.Ontology.Constants.MachineType;
import ak.main.Ontology.Sensors.BeltAlignmentSensor;
import ak.main.Ontology.Sensors.Constants.SensorTypes;
import ak.main.Ontology.Sensors.CurrentSensor;
import ak.main.Ontology.Sensors.Dto.SensorThreshold;
import ak.main.Ontology.Sensors.TemperatureSensor;
import ak.main.Ontology.Sensors.VibrationSensor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class AutomaticConveyor extends AbstractMachine {
    public AutomaticConveyor() {
        sensorThresholds.addAll(Arrays.asList(
                new SensorThreshold(SensorTypes.TEMPERATURE.getSensorType(), 30, 80, 28, 90),
                new SensorThreshold(SensorTypes.VIBRATION.getSensorType(), 1.5, null, 2.0),
                new SensorThreshold(SensorTypes.BELT_ALIGNMENT.getSensorType(), -2, 2, -2.5, 2.5),
                new SensorThreshold(SensorTypes.CURRENT.getSensorType(), 5, 20, 4, 25)
        ));

        sensorMap.putAll(Map.of(
                SensorTypes.TEMPERATURE, TemperatureSensor.class.getName(),
                SensorTypes.VIBRATION, VibrationSensor.class.getName(),
                SensorTypes.BELT_ALIGNMENT, BeltAlignmentSensor.class.getName(),
                SensorTypes.CURRENT, CurrentSensor.class.getName()
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
}
