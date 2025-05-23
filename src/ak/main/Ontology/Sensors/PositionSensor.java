package ak.main.Ontology.Sensors;

import ak.main.Ontology.Sensors.Constants.SensorTypes;

import java.util.Random;

//METRIC: mm
public class PositionSensor extends AbstractSensor {
    private final Random random = new Random();

    @Override
    public SensorTypes getSensorType() {
        return SensorTypes.POSITION;
    }

    @Override
    public double getReading() {
        return -4 + random.nextDouble() * 8;
    }
}
