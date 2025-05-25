package ak.main.Agents;

import ak.main.Ontology.CarFactoryOntology;
import ak.main.Ontology.Constants.MachineType;
import ak.main.Ontology.Machines.AbstractMachine;
import ak.main.Ontology.Sensors.AbstractSensor;
import ak.main.Ontology.Sensors.Dto.SensorAlert;
import jade.content.lang.sl.SLCodec;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

import java.util.ArrayList;

public class MaintenanceAgent extends Agent {
    protected void setup() {
        getContentManager().registerLanguage(new SLCodec());
        getContentManager().registerOntology(CarFactoryOntology.ontologyInstance);

        addBehaviour(new MaintenanceRequestReceiver());
    }

    private class MaintenanceRequestReceiver extends CyclicBehaviour {
        public void action() {
            MessageTemplate template = MessageTemplate.and(
                    MessageTemplate.MatchOntology(CarFactoryOntology.CAR_FACTORY_ONTOLOGY),
                    MessageTemplate.MatchPerformative(ACLMessage.REQUEST)
            );

            ACLMessage msg = myAgent.receive(template);
            if (msg != null) {
                try {
                    ArrayList<SensorAlert> criticalAlerts = (ArrayList<SensorAlert>) msg.getContentObject();
                    String conversationId = msg.getConversationId();
                    MachineType machineType = criticalAlerts.get(0).getMachineType();

                    System.out.println("Received maintenance request for machine " + machineType);

                    ACLMessage response = new ACLMessage(ACLMessage.INFORM);
                    response.addReceiver(msg.getSender());
                    response.setOntology(CarFactoryOntology.CAR_FACTORY_ONTOLOGY);
                    response.setLanguage(FIPANames.ContentLanguage.FIPA_SL);
                    response.setConversationId(conversationId);
                    response.setReplyWith(conversationId);
                    response.setContent("REPAIRS STARTED for machine: " + machineType);
                    send(response);

                    AbstractMachine underRepairMachine = machineType.getMachine();
                    ArrayList<AbstractSensor> machineSensors = underRepairMachine.getSensors();

                    for (AbstractSensor sensor : machineSensors) {
                        boolean repairResult = sensor.repair();
                        if (!repairResult) {
                            sensor.replace();
                        }
                    }

                    ACLMessage repairCompletedResponse = new ACLMessage(ACLMessage.INFORM);
                    repairCompletedResponse.addReceiver(msg.getSender());
                    repairCompletedResponse.setOntology(CarFactoryOntology.CAR_FACTORY_ONTOLOGY);
                    repairCompletedResponse.setLanguage(FIPANames.ContentLanguage.FIPA_SL);
                    repairCompletedResponse.setConversationId(conversationId);
                    repairCompletedResponse.setReplyWith(conversationId);
                    repairCompletedResponse.setContent("REPAIRED for machine: " + machineType);
                    send(repairCompletedResponse);
                } catch (UnreadableException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else {
                block();
            }
        }
    }
}