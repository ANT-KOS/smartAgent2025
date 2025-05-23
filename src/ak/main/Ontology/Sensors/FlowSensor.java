package ak.main.Ontology.Sensors;

import ak.main.Ontology.Sensors.constants.SensorTypes;

import java.util.Random;

//METRIC L/min
public class FlowSensor extends Sensor {
    private final Random random = new Random();

    @Override
    public SensorTypes getSensorType() {
        return SensorTypes.FLOW;
    }

    @Override
    public double getReading() {
        return 5 + random.nextDouble() * 80;
    }
}
