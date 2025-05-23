package ak.main.Ontology.Sensors;

import java.util.Random;

//METRIC ml/min
public class SprayGunFlowSensor extends FlowSensor {
    private Random random = new Random();

    @Override
    public double getReading() {
        return 90 + random.nextDouble() + 220;
    }
}
