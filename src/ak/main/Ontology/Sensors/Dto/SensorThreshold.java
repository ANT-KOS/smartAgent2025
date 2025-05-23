package ak.main.Ontology.Sensors.Dto;

import jade.content.Concept;

public class SensorThreshold implements Concept {
    private final String sensorType;
    private final Double threshold;
    private final Double min;
    private final Double max;

    public SensorThreshold(String sensorType, double threshold) {
        this.sensorType = sensorType;
        this.threshold = threshold;
        this.min = null;
        this.max = null;
    }

    public SensorThreshold(String sensorType, double min, double max) {
        this.sensorType = sensorType;
        this.min = min;
        this.max = max;
        this.threshold = null;
    }

    public String getSensorType() {
        return sensorType;
    }

    public Double getThreshold() {
        return threshold;
    }

    public Double getMin() {
        return min;
    }

    public Double getMax() {
        return max;
    }
}
