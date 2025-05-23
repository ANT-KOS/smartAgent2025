package ak.main.Ontology.Sensors.constants;

public enum SensorTypes {
    TEMPERATURE("temperature"),
    HUMIDITY("humidity"),
    PRESSURE("pressure"),
    VOLTAGE("voltage"),
    PARTICLE("particle"),
    VIBRATION("vibration"),
    SPINDLE_RPM("spindle_rpm"),
    CURRENT("current"),
    COOLANT_FLOW("coolant_flow"),
    POSITION("position"),
    AIR_PRESSURE("air_pressure"),
    FLOW("flow");

    private final String sensorType;

    SensorTypes(String sensorType) {
        this.sensorType = sensorType;
    }

    public String getSensorType() {
        return sensorType;
    }
}
