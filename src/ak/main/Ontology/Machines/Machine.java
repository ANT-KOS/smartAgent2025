package ak.main.Ontology.Machines;

import ak.main.Ontology.Sensors.Dto.SensorThreshold;
import jade.content.Concept;

import java.util.List;

abstract class Machine implements Concept {
    protected String status;
    public abstract List<SensorThreshold> getSensorThresholds();
    public abstract String getMachineType();
    public abstract void setStatus(String status);
    public abstract String getStatus();
}
