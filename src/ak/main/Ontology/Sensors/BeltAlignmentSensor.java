package ak.main.Ontology.Sensors;

import ak.main.Ontology.Sensors.Constants.SensorTypes;

import java.util.Random;

//METRIC mm
public class BeltAlignmentSensor extends AbstractSensor{
    private final Random random = new Random();

    @Override
    public SensorTypes getSensorType() {
        return SensorTypes.BELT_ALIGNMENT;
    }

    @Override
    public double getReading() {
        return -4.0 + random.nextDouble() * 8.0;
    }
}
