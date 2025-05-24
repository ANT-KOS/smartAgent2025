package ak.main.Ontology.Sensors.Dto;

import jade.content.Concept;

public class SensorThreshold implements Concept {
    private final String sensorType;
    private final Double threshold;
    private final Double min;
    private final Double max;
    private final Double minSevereThreshold;
    private final Double maxSevereThreshold;

    public SensorThreshold(String sensorType, double threshold, Double minSevereThreshold, Double maxSevereThreshold) {
        this.sensorType = sensorType;
        this.threshold = threshold;
        this.min = null;
        this.max = null;
        this.minSevereThreshold = minSevereThreshold;
        this.maxSevereThreshold = maxSevereThreshold;
    }

    public SensorThreshold(String sensorType, double min, double max, double minSevereThreshold, double maxSevereThreshold) {
        this.sensorType = sensorType;
        this.min = min;
        this.max = max;
        this.threshold = null;
        this.minSevereThreshold = minSevereThreshold;
        this.maxSevereThreshold = maxSevereThreshold;
    }

    public String getSensorType() {
        return sensorType;
    }

    public boolean isWithinThreshold(Double value) {
        if (threshold != null) {
            return value <= threshold;
        } else if (min != null && max != null) {
            return value >= min && value <= max;
        }

        return true;
    }

    public boolean isCritical(Double value) {
        if (minSevereThreshold != null && maxSevereThreshold == null) {
            return value < minSevereThreshold;
        } else if (minSevereThreshold == null && maxSevereThreshold != null) {
            return value > maxSevereThreshold;
        } else if (minSevereThreshold != null && maxSevereThreshold != null) {
            return value < minSevereThreshold || value > maxSevereThreshold;
        }
        return false;
    }
}
