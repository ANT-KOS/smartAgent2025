package ak.main.Ontology.Machines;

import ak.main.Ontology.Constants.MachineType;
import ak.main.Ontology.Sensors.AbstractSensor;
import ak.main.Ontology.Sensors.Dto.SensorThreshold;
import jade.content.Concept;

import java.util.ArrayList;

public abstract class Machine implements Concept {
    protected String status;
    public abstract ArrayList<SensorThreshold> getSensorThresholds();
    public abstract ArrayList<AbstractSensor> getSensors();
    public abstract MachineType getMachineType();
    public abstract Machine setStatus(String status);
    public abstract String getStatus();
}
