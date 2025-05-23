package ak.main.Ontology.Sensors;

import java.util.Random;

public class WeldingTemperatureSensor extends TemperatureSensor {
    private final Random random = new Random();

    @Override
    public double getReading() {
        return 1100 + random.nextDouble() * 500;
    }
}
