package ak.main.Agents;

import ak.main.Ontology.CarFactoryOntology;
import ak.main.Ontology.Machines.AbstractMachine;
import ak.main.Ontology.Sensors.AbstractSensor;
import ak.main.Ontology.Sensors.Constants.SensorTypes;
import ak.main.Ontology.Sensors.Dto.SensorAlert;
import ak.main.Ontology.Sensors.Dto.SensorThreshold;
import jade.content.lang.sl.SLCodec;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

import java.io.IOException;
import java.util.ArrayList;

public class SensorAgent extends Agent {
    private AbstractMachine machine;
    private AID coordinatorAgent;

    @Override
    protected void setup() {
        coordinatorAgent = new AID("CoordinatorAgent", AID.ISLOCALNAME);
        getContentManager().registerLanguage(new SLCodec());
        getContentManager().registerOntology(CarFactoryOntology.ontologyInstance);

        Object[] args = getArguments();
        if (args != null && args.length > 0 && args[0] instanceof AbstractMachine) {
            this.machine = (AbstractMachine) args[0];

            addBehaviour(new TickerBehaviour(this, 1000) {
                @Override
                protected void onTick() {
                    try {
                        checkSensors();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        } else {
            System.out.println("No argument supplied to SensorAgent");
            doDelete();
        }
    }

    private void checkSensors() throws IOException {
        ArrayList<SensorAlert> alerts = new ArrayList<>();

        for (AbstractSensor sensor : machine.getSensors()) {
            double reading = sensor.getReading();
            SensorTypes sensorType = sensor.getSensorType();

            for (SensorThreshold threshold : machine.getSensorThresholds()) {
                if (threshold.getSensorType().equals(sensorType.getSensorType())) {
                    if (!threshold.isWithinThreshold(reading)) {
                        System.out.println(sensor.getSensorType() + " has exceeded the threshold " + threshold.getSensorType());
                        alerts.add(new SensorAlert()
                                .setMachineType(machine.getMachineType())
                                .setSensorType(sensorType)
                                .setCritical(threshold.isCritical(reading)));
                    }
                }
            }
        }

        if (!alerts.isEmpty()) {
            ACLMessage alertMessage = new ACLMessage(ACLMessage.INFORM);
            alertMessage.addReceiver(coordinatorAgent);
            alertMessage.setOntology(CarFactoryOntology.CAR_FACTORY_ONTOLOGY);
            alertMessage.setContentObject(alerts);
            send(alertMessage);
        }
    }
}