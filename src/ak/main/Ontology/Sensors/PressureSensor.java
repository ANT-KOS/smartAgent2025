package ak.main.Ontology.Sensors;

import ak.main.Ontology.Sensors.constants.SensorTypes;

import java.util.Random;

//METRIC: Pa (Pascals)
public class PressureSensor extends Sensor {
    private final Random random = new Random();

    @Override
    public SensorTypes getSensorType() {
        return SensorTypes.PRESSURE;
    }

    @Override
    public double getReading() {
        return 20 + random.nextDouble() * 50;
    }
}
