package ak.main.Ontology.Sensors;

import ak.main.Ontology.Sensors.Constants.SensorTypes;

import java.util.Random;

//METRIC: RPM
public class SpindleSensor extends AbstractSensor {
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
