package ak.main.Ontology.Sensors.Constants;

public enum SensorTypes {
    TEMPERATURE("temperature"),
    HUMIDITY("humidity"),
    PRESSURE("pressure"),
    VOLTAGE("voltage"),
    PARTICLE("particle"),
    VIBRATION("vibration"),
    SPINDLE_RPM("spindle_rpm"),
    CURRENT("current"),
    POSITION("position"),
    FLOW("flow"),
    BELT_ALIGNMENT("belt_alignment"),
    FUME_CONCENTRATION("fume_concentration");
    private final String sensorType;

    SensorTypes(String sensorType) {
        this.sensorType = sensorType;
    }

    public String getSensorType() {
        return sensorType;
    }
}
