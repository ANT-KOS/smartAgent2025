package ak.main.Agents;

import ak.main.Behaviours.MaintenanceRequestReceiver;
import ak.main.Ontology.CarFactoryOntology;
import jade.content.lang.sl.SLCodec;
import jade.core.Agent;


public class MaintenanceAgent extends Agent {
    protected void setup() {
        getContentManager().registerLanguage(new SLCodec());
        getContentManager().registerOntology(CarFactoryOntology.ontologyInstance);

        addBehaviour(new MaintenanceRequestReceiver(this));
    }
}