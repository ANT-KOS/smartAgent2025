package ak.main.Ontology.Sensors;

import ak.main.Ontology.Sensors.Constants.SensorTypes;
import jade.content.Concept;

import java.util.Random;

public abstract class AbstractSensor implements Concept {
    public final int MAX_ATTEMPTS = 5;
    public abstract SensorTypes getSensorType();
    public abstract double getReading();
    public boolean repair() {
        Random random = new Random();
        for (int i = 0; i < MAX_ATTEMPTS; i++) {
            boolean success = random.nextBoolean();
            System.out.println("Attempt " + (i + 1) + " to repair: " + this.getSensorType().getSensorType());

            if (success) {
                System.out.println("Repair Success");
                return true;
            }
        }

        System.out.println("Repair Failed after " + MAX_ATTEMPTS + " attempts");
        return false;
    }

    public boolean replace() throws InterruptedException {
        System.out.println("Replacing broken component...");
        wait(5000);
        return true;
    }
}
