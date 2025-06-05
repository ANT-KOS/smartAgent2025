package ak.main.Agents;

import ak.main.Behaviours.Warehouse.MaterialRequestReceiver;
import ak.main.Ontology.CarFactoryOntology;
import jade.content.lang.sl.SLCodec;
import jade.core.Agent;

public class WarehouseAgent extends Agent {
    protected void setup() {
        getContentManager().registerLanguage(new SLCodec());
        getContentManager().registerOntology(CarFactoryOntology.ontologyInstance);

        addBehaviour(new MaterialRequestReceiver(this));
    }
}
