package ak.main.Ontology;

import ak.main.Ontology.Sensors.Dto.SensorAlert;
import jade.content.onto.BasicOntology;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.schema.ConceptSchema;
import jade.content.schema.PrimitiveSchema;

import java.util.ArrayList;

public class CarFactoryOntology extends Ontology {
    public static final String CAR_FACTORY_ONTOLOGY = "CarFactoryOntology";
    public static final CarFactoryOntology ontologyInstance = new CarFactoryOntology();

    public static final String SENSOR_ALERT = "SensorAlert";
    public static final String SENSOR_ALERT_LIST = "SensorAlertList";

    private CarFactoryOntology() {
        super(CAR_FACTORY_ONTOLOGY, BasicOntology.getInstance());

        try {
            //Probably not needed since i send an array list
            ConceptSchema sensorAlertSchema = new ConceptSchema(SENSOR_ALERT);
            add(sensorAlertSchema, SensorAlert.class);
            sensorAlertSchema.add("machineId", (PrimitiveSchema) getSchema(BasicOntology.STRING));
            sensorAlertSchema.add("sensorType", (PrimitiveSchema) getSchema(BasicOntology.STRING));
            sensorAlertSchema.add("isCritical", (PrimitiveSchema) getSchema(BasicOntology.BOOLEAN));

            ConceptSchema sensorAlertListSchema = new ConceptSchema(SENSOR_ALERT_LIST);
            sensorAlertListSchema.add(SENSOR_ALERT_LIST, (ConceptSchema) getSchema(SENSOR_ALERT));
            add(sensorAlertListSchema, ArrayList.class);


        } catch (OntologyException e) {
            throw new RuntimeException(e);
        }
    }
}
