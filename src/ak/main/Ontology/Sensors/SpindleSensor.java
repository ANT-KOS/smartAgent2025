package ak.main.Ontology.Sensors;

import ak.main.Ontology.Sensors.constants.SensorTypes;

import java.util.Random;

//METRIC: RPM
public class SpindleSensor extends Sensor {
    private final Random random = new Random();

    @Override
    public SensorTypes getSensorType() {
        return SensorTypes.SPINDLE_RPM;
    }

    @Override
    public double getReading() {
        return 500 + random.nextDouble() * 10000;
    }
}
