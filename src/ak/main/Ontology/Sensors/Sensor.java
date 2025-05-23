package ak.main.Ontology.Sensors;

import ak.main.Ontology.Sensors.constants.SensorTypes;

abstract class Sensor {
    public abstract SensorTypes getSensorType();
    public abstract double getReading();
}
