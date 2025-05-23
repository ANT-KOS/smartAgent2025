package ak.main.Ontology.Sensors;

import ak.main.Ontology.Sensors.constants.SensorTypes;

import java.util.Random;

//METRIC: A
public class CurrentSensor extends Sensor {
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
