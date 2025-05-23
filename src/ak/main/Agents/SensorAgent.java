package ak.main.Agents;

import ak.main.Ontology.CarFactoryOntology;
import ak.main.Ontology.Machines.Machine;
import ak.main.Ontology.Sensors.AbstractSensor;
import ak.main.Ontology.Sensors.Constants.SensorTypes;
import ak.main.Ontology.Sensors.Dto.SensorThreshold;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

public class SensorAgent extends Agent {
    private Machine machine;
    private AID coordinatorAgent;

    @Override
    protected void setup() {
        Object[] args = getArguments();
        if (args != null && args.length > 0 && args[0] instanceof Machine) {
            this.machine = (Machine) args[0];

            addBehaviour(new TickerBehaviour(this, 1000) {
                @Override
                protected void onTick() {
                    checkSensors();
                }
            });
        } else {
            System.out.println("No argument supplied to SensorAgent");
            doDelete();
        }
    }

    private void checkSensors() {
        for (AbstractSensor sensor : machine.getSensors()) {
            double reading = sensor.getReading();
            SensorTypes sensorType = sensor.getSensorType();

            for (SensorThreshold threshold : machine.getSensorThresholds()) {
                if (threshold.getSensorType().equals(sensorType.getSensorType())) {
                    if (!threshold.isWithinThreshold(reading)) {
                        System.out.println(sensor.getSensorType() + " is exceeded the threshold " + threshold.getSensorType());

                        ACLMessage alert = new ACLMessage(ACLMessage.INFORM);
                        alert.addReceiver(new AID("CoordinatorAgent", AID.ISLOCALNAME));
                        alert.setOntology(CarFactoryOntology.CAR_FACTORY_ONTOLOGY);
                        alert.setContent("ALERT: " + machine.getMachineType() + " " + sensor.getSensorType() + " reading " + reading);
                        send(alert);
                    }
                    break;
                }
            }
        }
    }
}