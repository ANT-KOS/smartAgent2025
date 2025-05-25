package ak.main.Agents;

import ak.main.Ontology.CarFactoryOntology;
import ak.main.Ontology.Constants.MachineStatus;
import ak.main.Ontology.Constants.MachineType;
import ak.main.Ontology.Sensors.Dto.SensorAlert;
import jade.content.lang.sl.SLCodec;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CoordinatorAgent extends Agent {
    private Map<MachineType, ArrayList<SensorAlert>> machineAlerts = new HashMap<>();
    private Map<MachineType, MachineStatus> machineStatusPerMachine = new HashMap<>();
    private AID maintenanceAgent;

    protected void setup() {
        machineStatusPerMachine.putAll(Map.of(
                MachineType.CNC_MACHINE, MachineStatus.OPERATING,
                MachineType.AUTOMATED_PAINTING, MachineStatus.OPERATING,
                MachineType.AUTOMATIC_CONVEYOR, MachineStatus.OPERATING,
                MachineType.HYDRAULIC_PRESS, MachineStatus.OPERATING,
                MachineType.ROBOTIC_WELDER, MachineStatus.OPERATING
        ));

        getContentManager().registerLanguage(new SLCodec());
        getContentManager().registerOntology(CarFactoryOntology.ontologyInstance);
        maintenanceAgent = new AID("maintenanceAgent", AID.ISLOCALNAME);

        addBehaviour(new SensorAlertReceiver());
    }

    private class SensorAlertReceiver extends CyclicBehaviour {
        public void action() {
            MessageTemplate template = MessageTemplate.and(
                    MessageTemplate.MatchOntology(CarFactoryOntology.CAR_FACTORY_ONTOLOGY),
                    MessageTemplate.MatchPerformative(ACLMessage.INFORM)
            );

            ACLMessage msg = myAgent.receive(template);
            if (msg != null && msg.getSender().getLocalName().startsWith("SensorAgent_")) {
                try{
                    ArrayList<SensorAlert> alerts = (ArrayList<SensorAlert>) msg.getContentObject();
                    ArrayList<SensorAlert> criticalAlerts = new ArrayList<>();

                    if (!alerts.isEmpty() && machineStatusPerMachine.get(alerts.getFirst().getMachineType()).equals(MachineStatus.OPERATING)) {
                        MachineType machineType = alerts.getFirst().getMachineType();

                        for (SensorAlert alert : alerts) {
                            machineAlerts.computeIfAbsent(alert.getMachineType(), k -> new ArrayList<>()).add(alert);

                            System.out.println("Received alert for machine " + alert.getMachineType() + ": " + alert.getSensorType());

                            if (alert.isCritical()) {
                                criticalAlerts.add(alert);
                            }
                        }

                        if (!criticalAlerts.isEmpty()) {
                            String conversationId = "maintenance-" + machineType.getMachineType() + "-" + System.currentTimeMillis();

                            ACLMessage maintenanceRequest = new ACLMessage(ACLMessage.REQUEST);
                            maintenanceRequest.addReceiver(maintenanceAgent);
                            maintenanceRequest.setOntology(CarFactoryOntology.CAR_FACTORY_ONTOLOGY);
                            maintenanceRequest.setLanguage(FIPANames.ContentLanguage.FIPA_SL);
                            maintenanceRequest.setContentObject(criticalAlerts);
                            maintenanceRequest.setConversationId(conversationId);
                            maintenanceRequest.setReplyWith(conversationId);
                            send(maintenanceRequest);

                            machineStatusPerMachine.put(machineType, MachineStatus.FAULTY);
                        }
                    }
                } catch (UnreadableException | IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                block();
            }
        }
    }

    private class MaintenanceResponseReceiver extends CyclicBehaviour {
        public void action() {
            MessageTemplate template = MessageTemplate.and(
                    MessageTemplate.MatchOntology(CarFactoryOntology.CAR_FACTORY_ONTOLOGY),
                    MessageTemplate.MatchPerformative(ACLMessage.INFORM)
            );

            ACLMessage msg = myAgent.receive(template);
            if (msg != null && msg.getSender().equals(maintenanceAgent)) {
                if (!msg.getConversationId().isEmpty() &&
                        msg.getConversationId().equals(msg.getReplyWith())) {
                    String response = msg.getContent();
                    if (response.contains("REPAIRED")) {
                        MachineType machineType = extractMachineType(response);
                        if (!machineStatusPerMachine.get(machineType).equals(MachineStatus.OPERATING)) {
                            machineStatusPerMachine.put(machineType, MachineStatus.OPERATING);
                        }
                    } else if (response.contains("REPAIRS STARTED")) {
                        MachineType machineType = extractMachineType(response);
                        if (!machineStatusPerMachine.get(machineType).equals(MachineStatus.UNDER_MAINTENANCE)) {
                            machineStatusPerMachine.put(machineType, MachineStatus.UNDER_MAINTENANCE);
                        }
                    }
                }
            } else {
                block();
            }
        }
    }

    private MachineType extractMachineType(String response) {
        return MachineType.valueOf(response.split(" ")[3].toUpperCase());
    }
}