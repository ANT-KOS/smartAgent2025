package ak.main.Ontology.Sensors;

import ak.main.Ontology.Sensors.Constants.SensorTypes;

import java.util.Random;

//METRIC: Percentage
public class HumiditySensor extends AbstractSensor {
    private final Random random = new Random();

    @Override
    public SensorTypes getSensorType() {
        return SensorTypes.HUMIDITY;
    }

    @Override
    public double getReading() {
        return 30 + random.nextGaussian() * 50;
    }
}
