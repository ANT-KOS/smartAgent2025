package ak.main.Ontology.Sensors;

import ak.main.Ontology.Sensors.Constants.SensorTypes;

import java.util.Random;

//METRIC: A
public class CurrentSensor extends AbstractSensor {
    private final Random random = new Random();

    @Override
    public SensorTypes getSensorType() {
        return SensorTypes.CURRENT;
    }

    @Override
    public double getReading() {
        return 10 + random.nextDouble() * 30;
    }
}
