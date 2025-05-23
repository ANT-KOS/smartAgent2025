package ak.main.Ontology.Sensors;

import ak.main.Ontology.Sensors.Constants.SensorTypes;

import java.util.Random;

//METRIC μg/m^3
public class FumeDetectionSensor extends AbstractSensor {
    private final Random random = new Random();

    @Override
    public SensorTypes getSensorType() {
        return SensorTypes.FUME_CONCENTRATION;
    }

    @Override
    public double getReading() {
        return 10 + random.nextDouble() * 80;
    }
}
