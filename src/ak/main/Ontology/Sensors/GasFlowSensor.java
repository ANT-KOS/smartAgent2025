package ak.main.Ontology.Sensors;

import java.util.Random;

//METRIC L/min
public class GasFlowSensor extends FlowSensor{
    private final Random random = new Random();

    @Override
    public double getReading() {
        return 8 + random.nextDouble() + 20;
    }
}
