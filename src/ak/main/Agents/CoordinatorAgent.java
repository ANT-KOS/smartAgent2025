package ak.main.Agents;

import ak.main.Ontology.CarFactoryOntology;
import ak.main.Ontology.Constants.MachineStatus;
import ak.main.Ontology.Constants.MachineType;
import ak.main.Ontology.Sensors.Dto.SensorAlert;
import jade.content.lang.sl.SLCodec;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CoordinatorAgent extends Agent {
    private Map<String, ArrayList<SensorAlert>> machineAlerts = new HashMap<>();
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
            if (msg != null) {
                try{
                    ArrayList<SensorAlert> alerts = (ArrayList<SensorAlert>) msg.getContentObject();
                    if (!alerts.isEmpty()) {
                        for (SensorAlert alert : alerts) {
                            MachineType machineType = alert.getMachineType();
                            machineAlerts.get(machineType).add(alert);

                            System.out.println("Received alert for machine " + machineType + ": " + alert.getSensorType());

                            if (alert.isCritical() && machineStatusPerMachine.get(machineType) == MachineStatus.OPERATING) {

                            }
                        }
                    }
                } catch (UnreadableException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}