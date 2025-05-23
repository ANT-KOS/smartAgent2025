package ak.main.Ontology;

import ak.main.Ontology.Machines.CncMachine;
import ak.main.Ontology.Constants.MachineType;
import ak.main.Ontology.Sensors.SensorThreshold;
import ak.main.Ontology.Sensors.constants.SensorTypes;
import jade.content.onto.BasicOntology;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.schema.ConceptSchema;
import jade.content.schema.ObjectSchema;
import jade.content.schema.PrimitiveSchema;

public class CarFactoryOntology extends Ontology {
    public static final String CAR_FACTORY_ONTOLOGY = "CarFactoryOntology";
    public static final CarFactoryOntology ontologyInstance = new CarFactoryOntology();

    public static final String SENSOR_THRESHOLD = "SensorThreshold";
    public static final String SENSOR_TYPES = "SensorTypes";

    private CarFactoryOntology() {
        super(CAR_FACTORY_ONTOLOGY, BasicOntology.getInstance());

        try {
            ConceptSchema sensorTypesSchema = new ConceptSchema(SENSOR_TYPES);
            add(sensorTypesSchema, SensorTypes.class);
            sensorTypesSchema.add("sensorType", (PrimitiveSchema) getSchema(BasicOntology.STRING));

            ConceptSchema sensorThresholdSchema = new ConceptSchema(SENSOR_THRESHOLD);
            add(sensorThresholdSchema, SensorThreshold.class);

            sensorThresholdSchema.add("sensorType", (PrimitiveSchema) getSchema(BasicOntology.STRING));
            sensorThresholdSchema.add("threshold", (PrimitiveSchema) getSchema(BasicOntology.FLOAT), 0, ObjectSchema.UNLIMITED);
            sensorThresholdSchema.add("minValue", (PrimitiveSchema) getSchema(BasicOntology.FLOAT));
            sensorThresholdSchema.add("maxValue", (PrimitiveSchema) getSchema(BasicOntology.FLOAT));

            for (MachineType machineType : MachineType.values()) {
                ConceptSchema machineConcreteClassesSchema = new ConceptSchema(machineType.getMachineType());
                switch (machineType) {
                    case MachineType.CNC_MACHINE:
                        add(machineConcreteClassesSchema, CncMachine.class);
                        break;
                    case MachineType.HYDRAULIC_PRESS:
                        break;
                    default:
                        break;

                }

                machineConcreteClassesSchema.add("machineType", (PrimitiveSchema) getSchema(BasicOntology.STRING));
                machineConcreteClassesSchema.add("sensorThresholds", sensorThresholdSchema, 0, ObjectSchema.UNLIMITED);
            }
        } catch (OntologyException e) {
            throw new RuntimeException(e);
        }
    }
}
