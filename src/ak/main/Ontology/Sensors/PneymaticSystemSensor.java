package ak.main.Ontology.Sensors;

import ak.main.Ontology.Sensors.constants.SensorTypes;

import java.util.Random;

//METRIC: bar
public class PneymaticSystemSensor extends Sensor {
    private final Random random = new Random();

    @Override
    public SensorTypes getSensorType() {
        return SensorTypes.AIR_PRESSURE;
    }

    @Override
    public double getReading() {
        return 5 + random.nextDouble() * 10;
    }
}
