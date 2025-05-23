package ak.main.Ontology.Sensors;

import java.util.Random;

public class ArcVoltageSensor extends VoltageSensor {
    private final Random random = new Random();

    @Override
    public double getReading() {
        return 15 + random.nextDouble() + 25;
    }
}
