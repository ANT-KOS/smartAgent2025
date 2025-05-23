package ak.main.Ontology.Sensors;

import ak.main.Ontology.Sensors.Constants.SensorTypes;

import java.util.Random;

//METRIC: ppm
public class ParticleSensor extends AbstractSensor {
    private final Random random = new Random();

    @Override
    public SensorTypes getSensorType() {
        return SensorTypes.PARTICLE;
    }

    @Override
    public double getReading() {
        return random.nextDouble() + 100;
    }
}
