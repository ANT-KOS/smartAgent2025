package ak.main.Ontology.Sensors;

import ak.main.Ontology.Sensors.constants.SensorTypes;

import java.util.Random;

//METRIC: mm
public class PositionSensor extends Sensor {
    private final Random random = new Random();

    @Override
    public SensorTypes getSensorType() {
        return SensorTypes.POSITION;
    }

    @Override
    public double getReading() {
        return 0 + random.nextDouble() * 0.2;
    }
}
