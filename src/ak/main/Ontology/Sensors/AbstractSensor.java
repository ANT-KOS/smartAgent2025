package ak.main.Ontology.Sensors;

import ak.main.Ontology.Sensors.Constants.SensorTypes;
import jade.content.Concept;

public abstract class AbstractSensor implements Concept {
    public abstract SensorTypes getSensorType();
    public abstract double getReading();
}
