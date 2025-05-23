package ak.main.Ontology.Sensors.Constants;

import ak.main.Ontology.Sensors.*;

public enum SensorClasses {
    ARC_VOLTAGE(ArcVoltageSensor.class),
    BELT_ALIGNMENT(BeltAlignmentSensor.class),
    COOLANT_FLOW(CoolantFlowSensor.class),
    CURRENT(CurrentSensor.class),
    FLOW(FlowSensor.class),
    FUME_DETECTION(FumeDetectionSensor.class),
    GAS_FLOW(GasFlowSensor.class),
    HUMIDITY(HumiditySensor.class),
    PARTICLE(ParticleSensor.class),
    PNEYMATIC_SYSTEM(PneymaticSystemSensor.class),
    POSITION(PositionSensor.class),
    PRESSURE(PressureSensor.class),
    SPINDLE(SpindleSensor.class),
    TEMPERATURE(TemperatureSensor.class),
    VIBRATION(VibrationSensor.class),
    VOLTAGE(VoltageSensor.class),
    WELDING_CURRENT(CurrentSensor.class),
    WELDING_TEMPERATURE(TemperatureSensor.class),
    SPRAY_GUN_FLOW(SprayGunFlowSensor.class);

    private final Class<? extends AbstractSensor> sensorClass;

    SensorClasses(Class<? extends AbstractSensor> sensorClass) {
        this.sensorClass = sensorClass;
    }

    public Class<? extends AbstractSensor> getSensorClass() {
        return sensorClass;
    }
}
