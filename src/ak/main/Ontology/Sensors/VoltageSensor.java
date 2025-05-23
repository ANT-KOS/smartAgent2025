package ak.main.Ontology.Sensors;

import ak.main.Ontology.Sensors.Constants.SensorTypes;

import java.util.Random;

//METRIC: V
public class VoltageSensor extends AbstractSensor {
    private final Random random = new Random();

    @Override
    public SensorTypes getSensorType() {
        return SensorTypes.VOLTAGE;
    }

    @Override
    public double getReading() {
        return 200 + random.nextDouble() * 100;
    }
}
