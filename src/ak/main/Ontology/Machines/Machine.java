package ak.main.Ontology.Machines;

import ak.main.Ontology.Sensors.SensorThreshold;
import jade.content.Concept;

import java.util.List;

abstract class Machine implements Concept {
    public abstract List<SensorThreshold> getSensorThresholds();
    public abstract String getMachineType();
}
