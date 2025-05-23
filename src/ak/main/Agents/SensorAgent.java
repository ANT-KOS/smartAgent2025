package ak.main.Agents;

import ak.main.Ontology.Sensors.constants.SensorTypes;
import jade.core.Agent;
import java.util.Map;

public class SensorAgent extends Agent {
    private Map<SensorTypes, Double> sensorThreshold;

    protected void setup() {
        Object[] args = getArguments();
        if (args != null && args.length >= 1) {


        }
    }
}