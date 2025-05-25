package ak.main.Ontology.Machines;

import ak.main.Ontology.Constants.MachineType;
import ak.main.Ontology.Sensors.Constants.SensorTypes;
import ak.main.Ontology.Sensors.Dto.SensorThreshold;
import ak.main.Ontology.Sensors.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class AutomatedPainting extends AbstractMachine {
    public AutomatedPainting() {
        sensorThresholds.addAll(Arrays.asList(
                new SensorThreshold(SensorTypes.PRESSURE.getSensorType(), 2, 4, 1, 5),
                new SensorThreshold(SensorTypes.TEMPERATURE.getSensorType(), 20, 30, 15, 35),
                new SensorThreshold(SensorTypes.HUMIDITY.getSensorType(), 40, 60, 35, 65),
                new SensorThreshold(SensorTypes.FLOW.getSensorType(), 100, 300, 90, 310),
                new SensorThreshold(SensorTypes.FUME_CONCENTRATION.getSensorType(), 5, null, 6.0)
        ));

        sensorMap.putAll(Map.of(
                SensorTypes.PRESSURE, PressureSensor.class.getName(),
                SensorTypes.TEMPERATURE, TemperatureSensor.class.getName(),
                SensorTypes.HUMIDITY, HumiditySensor.class.getName(),
                SensorTypes.FLOW, SprayGunFlowSensor.class.getName(),
                SensorTypes.FUME_CONCENTRATION, FumeDetectionSensor.class.getName()
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
}
