package ak.main.Ontology.Sensors;

import ak.main.Ontology.Sensors.constants.SensorTypes;

import java.util.Random;

//METRIC: L/min
public class CoolantSensor extends Sensor {
    private final Random random = new Random();

    @Override
    public SensorTypes getSensorType() {
        return SensorTypes.COOLANT_FLOW;
    }

    @Override
    public double getReading() {
        return 5 + random.nextDouble() * 10;
    }
}
