package ak.main.Ontology.Machines;

import ak.main.Ontology.Constants.MachineType;
import ak.main.Ontology.Sensors.AbstractSensor;
import ak.main.Ontology.Sensors.Constants.SensorTypes;
import ak.main.Ontology.Sensors.Dto.SensorThreshold;
import jade.content.Concept;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractMachine implements Concept {
    protected final ArrayList<SensorThreshold> sensorThresholds = new ArrayList<>();
    protected final Map<SensorTypes, String> sensorMap = new HashMap<>();
    public abstract ArrayList<SensorThreshold> getSensorThresholds();
    public abstract MachineType getMachineType();

    public ArrayList<AbstractSensor> getSensors() {
        ArrayList<AbstractSensor> sensors = new ArrayList<>();

        for (Map.Entry<SensorTypes, String> entry : sensorMap.entrySet()) {
            try {
                String className = entry.getValue();
                Class<?> clazz = Class.forName(className);

                AbstractSensor sensor = (AbstractSensor) clazz.getDeclaredConstructor().newInstance();

                sensors.add(sensor);
            } catch (ClassNotFoundException | InvocationTargetException | InstantiationException |
                     IllegalAccessException | NoSuchMethodException e) {
                throw new RuntimeException("Error instantiating sensor class", e);
            }
        }

        return sensors;
    }
}
