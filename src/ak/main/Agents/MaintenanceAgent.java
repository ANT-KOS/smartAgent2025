package ak.main.Agents;

import ak.main.Behaviours.Maintenance.MaintenanceRequestReceiver;
import ak.main.Ontology.CarFactoryOntology;
import jade.content.lang.sl.SLCodec;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;


public class MaintenanceAgent extends Agent {
    protected void setup() {
        getContentManager().registerLanguage(new SLCodec());
        getContentManager().registerOntology(CarFactoryOntology.ontologyInstance);

        //We add the agent and service descriptions in order to add the maintenance agents
        //in a category in the DF service. This is done so we can search for all maintenance
        //agents in the coordinator agent

        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());

        ServiceDescription sd = new ServiceDescription();
        sd.setType("maintenance");
        sd.setName(getLocalName());

        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        } catch (FIPAException e) {
            throw new RuntimeException(e);
        }
        addBehaviour(new MaintenanceRequestReceiver(this));
    }
}