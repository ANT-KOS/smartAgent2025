package ak.main.Agents;

import ak.main.Behaviours.Coordinator.MachineStartReplyReciever;
import ak.main.Behaviours.Coordinator.MachineStatusInspector;
import ak.main.Behaviours.Coordinator.MachineStopReplyReciever;
import ak.main.Behaviours.Coordinator.MaintenanceResponseReceiver;
import ak.main.Ontology.CarFactoryOntology;
import ak.main.Ontology.Constants.MachineStatus;
import ak.main.Ontology.Constants.MachineType;
import jade.content.lang.sl.SLCodec;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

import java.util.*;

public class CoordinatorAgent extends Agent {
    private Map<MachineType, MachineStatus> machineStatusPerMachine = new HashMap<>();
    private List<AID> unavailableMaintenanceAgents = new ArrayList<>();
    private List<AID> maintenanceAgents;

    protected void setup() {

        getContentManager().registerLanguage(new SLCodec());
        getContentManager().registerOntology(CarFactoryOntology.ontologyInstance);
        maintenanceAgents = Arrays.asList(queryMaintenanceAgents());

        addBehaviour(new MachineStartReplyReciever(this));
        addBehaviour(new MachineStopReplyReciever(this));
        addBehaviour(new MachineStatusInspector(this));
        addBehaviour(new MaintenanceResponseReceiver(this));
    }

    public synchronized void changeMachineStatus(MachineType machineType, MachineStatus newStatus) {
        machineStatusPerMachine.compute(machineType, (k, v) -> newStatus);
        System.out.println(machineStatusPerMachine);
    }

    public synchronized MachineStatus getMachineStatus(MachineType machineType) {
        return machineStatusPerMachine.get(machineType);
    }

    private AID[] queryMaintenanceAgents() {
        //Get all maintenance agents dynamically. We can add as much maintenance agents as we want,
        //and we could still see all of them

        DFAgentDescription template = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType("maintenance");
        template.addServices(sd);

        try {
            DFAgentDescription[] result = DFService.search(this, template);
            return Arrays.stream(result)
                    .map(DFAgentDescription::getName)
                    .filter(aid -> aid.getLocalName().startsWith("MaintenanceAgent_Maintenance-"))
                    .toArray(AID[]::new);
        } catch (FIPAException fe) {
            fe.printStackTrace();
            return new AID[0];
        }
    }

    //The coordinator agent keeps a status of the machines and the maintenance availability
    public synchronized void markMaintenanceAgentAsUnavailable(AID maintenanceAgent) {
        if (!unavailableMaintenanceAgents.contains(maintenanceAgent)) {
            unavailableMaintenanceAgents.add(maintenanceAgent);
            System.out.println("Maintenance agent " + maintenanceAgent.getLocalName() + " is now unavailable.");
        }
    }

    public synchronized void markMaintenanceAgentAsAvailable(AID maintenanceAgent) {
        if (unavailableMaintenanceAgents.contains(maintenanceAgent)) {
            unavailableMaintenanceAgents.remove(maintenanceAgent);
            System.out.println("Maintenance agent " + maintenanceAgent.getLocalName() + " is now available.");
            processQueuedMaintenanceRequests();
        }
    }

    public synchronized List<AID> getAvailableMaintenanceAgents() {
        ArrayList<AID> availableMaintenanceAgents = new ArrayList<>(maintenanceAgents);
        availableMaintenanceAgents.removeAll(unavailableMaintenanceAgents);
        return availableMaintenanceAgents;
    }

    public List<AID> getMaintenanceAgents() {
        return maintenanceAgents;
    }

    public void processQueuedMaintenanceRequests() {
        ACLMessage trigger = new ACLMessage(ACLMessage.INFORM);
        trigger.setOntology(CarFactoryOntology.CAR_FACTORY_ONTOLOGY);
        trigger.setConversationId("trigger-queue-processing");
        trigger.addReceiver(getAID());  // send to self
        send(trigger);
    }
}