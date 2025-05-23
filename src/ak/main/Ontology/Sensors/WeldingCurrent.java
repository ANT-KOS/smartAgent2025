package ak.main.Ontology.Sensors;

import java.util.Random;

public class WeldingCurrent extends CurrentSensor {
    private final Random random = new Random();

    @Override
    public double getReading() {
        return 90 + random.nextDouble() + 220;
    }
}
