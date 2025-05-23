package ak.main.Ontology.Sensors;

import java.util.Random;

//METRIC: L/min
public class CoolantFlowSensor extends FlowSensor {
    private final Random random = new Random();

    @Override
    public double getReading() {
        return 5 + random.nextDouble() * 10;
    }
}
