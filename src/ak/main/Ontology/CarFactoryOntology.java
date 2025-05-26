package ak.main.Ontology;

import ak.main.Ontology.Dto.MachineResponsesDto;
import ak.main.Ontology.Dto.MaintenanceRequestDto;
import ak.main.Ontology.Orders.MaterialRequest;
import jade.content.onto.BasicOntology;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.schema.ConceptSchema;
import jade.content.schema.PrimitiveSchema;

import java.util.ArrayList;

public class CarFactoryOntology extends Ontology {
    public static final String CAR_FACTORY_ONTOLOGY = "CarFactoryOntology";
    public static final CarFactoryOntology ontologyInstance = new CarFactoryOntology();

    public static final String MACHINE_RESPONSES_DTO = "MachineResponsesDto";
    public static final String MACHINE_RESPONSES_DTO_LIST = "MachineResponsesDtoList";
    public static final String MAINTENANCE_REQUEST_DTO = "MaintenanceRequestDto";
    public static final String MACHINE_RESPONSES = "machineResponse";
    public static final String MATERIAL_REQUEST = "materialRequest";

    private CarFactoryOntology() {
        super(CAR_FACTORY_ONTOLOGY, BasicOntology.getInstance());

        try {
            ConceptSchema machineResponsesSchema = new ConceptSchema(MACHINE_RESPONSES_DTO);
            add(machineResponsesSchema, MachineResponsesDto.class);
            machineResponsesSchema.add("machineType", (PrimitiveSchema) getSchema(BasicOntology.STRING));
            machineResponsesSchema.add("machineResponse", (PrimitiveSchema) getSchema(BasicOntology.STRING));

            ConceptSchema machineResponsesListSchema = new ConceptSchema(MACHINE_RESPONSES_DTO_LIST);
            machineResponsesListSchema.add(MACHINE_RESPONSES_DTO_LIST, (ConceptSchema) getSchema(MACHINE_RESPONSES));
            add(machineResponsesListSchema, ArrayList.class);

            ConceptSchema maintenanceRequestSchema = new ConceptSchema(MAINTENANCE_REQUEST_DTO);
            add(maintenanceRequestSchema, MaintenanceRequestDto.class);
            maintenanceRequestSchema.add("machineType", (PrimitiveSchema) getSchema(BasicOntology.STRING));
            maintenanceRequestSchema.add("machineResponse", (PrimitiveSchema) getSchema(BasicOntology.STRING));

            ConceptSchema materialRequestSchema = new ConceptSchema(MATERIAL_REQUEST);
            add(materialRequestSchema, MaterialRequest.class);
            materialRequestSchema.add("material", (PrimitiveSchema) getSchema(BasicOntology.STRING));
            materialRequestSchema.add("response", (PrimitiveSchema) getSchema(BasicOntology.STRING));
        } catch (OntologyException e) {
            throw new RuntimeException(e);
        }
    }
}
