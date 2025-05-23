package ak.main.Ontology.Sensors;

import ak.main.Ontology.Sensors.Constants.SensorTypes;

import java.util.Random;

//METRIC: Celcius
public class TemperatureSensor extends AbstractSensor {
    private final Random random = new Random();

    @Override
    public SensorTypes getSensorType() {
        return SensorTypes.TEMPERATURE;
    }

    @Override
    public double getReading() {
        return 45 + random.nextDouble() * 50;
    }
}
