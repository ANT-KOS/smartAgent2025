package ak.main.Ontology;

import ak.main.Ontology.Constants.MachineType;
import ak.main.Ontology.Machines.*;
import ak.main.Ontology.Sensors.Dto.SensorAlert;
import jade.content.onto.BasicOntology;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.schema.ConceptSchema;
import jade.content.schema.PrimitiveSchema;

public class CarFactoryOntology extends Ontology {
    public static final String CAR_FACTORY_ONTOLOGY = "CarFactoryOntology";
    public static final CarFactoryOntology ontologyInstance = new CarFactoryOntology();

    public static final String SENSOR_ALERT = "SensorAlert";

    private CarFactoryOntology() {
        super(CAR_FACTORY_ONTOLOGY, BasicOntology.getInstance());

        try {
            ConceptSchema sensorAlertSchema = new ConceptSchema(SENSOR_ALERT);
            add(sensorAlertSchema, SensorAlert.class);
            sensorAlertSchema.add("machineId", (PrimitiveSchema) getSchema(BasicOntology.STRING));
            sensorAlertSchema.add("sensorType", (PrimitiveSchema) getSchema(BasicOntology.STRING));
            sensorAlertSchema.add("isCritical", (PrimitiveSchema) getSchema(BasicOntology.BOOLEAN));

            for (MachineType machineType : MachineType.values()) {
                ConceptSchema machineConcreteClassesSchema = new ConceptSchema(machineType.getMachineType());

                boolean schemaAdded = false;
                switch (machineType) {
                    case MachineType.CNC_MACHINE:
                        add(machineConcreteClassesSchema, CncMachine.class);
                        schemaAdded = true;
                        break;
                    case MachineType.HYDRAULIC_PRESS:
                        add(machineConcreteClassesSchema, HydraulicPress.class);
                        schemaAdded = true;
                        break;
                    case MachineType.AUTOMATIC_CONVEYOR:
                        add(machineConcreteClassesSchema, AutomaticConveyor.class);
                        schemaAdded = true;
                        break;
                    case MachineType.AUTOMATED_PAINTING:
                        add(machineConcreteClassesSchema, AutomatedPainting.class);
                        schemaAdded = true;
                        break;
                    case MachineType.ROBOTIC_WELDER:
                        add(machineConcreteClassesSchema, RoboticWelder.class);
                        schemaAdded = true;
                        break;
                    default:
                        break;

                }

                if (schemaAdded) {
                    machineConcreteClassesSchema.add("machineType", (PrimitiveSchema) getSchema(BasicOntology.STRING));
                    machineConcreteClassesSchema.add("status", (PrimitiveSchema) getSchema(BasicOntology.STRING));
                }
            }
        } catch (OntologyException e) {
            throw new RuntimeException(e);
        }
    }
}
