package ak.main.Ontology.Sensors;

import ak.main.Ontology.Sensors.constants.SensorTypes;

import java.util.Random;

//METRIC: g(RMS)
public class VibrationSensor extends Sensor {
    private final Random random = new Random();

    @Override
    public SensorTypes getSensorType() {
        return SensorTypes.VIBRATION;
    }

    @Override
    public double getReading() {
        return 0.5 * random.nextDouble() * 1.5;
    }
}
