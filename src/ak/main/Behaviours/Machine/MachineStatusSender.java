package ak.main.Behaviours.Machine;

import ak.main.Ontology.CarFactoryOntology;
import ak.main.Ontology.Constants.MachineResponse;
import ak.main.Ontology.Dto.MachineResponsesDto;
import ak.main.Ontology.Machines.AbstractMachine;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

import java.io.IOException;
import java.util.ArrayList;

public class MachineStatusSender extends TickerBehaviour {
    private final AID coordinatorAgent;
    private final AbstractMachine machine;

    public MachineStatusSender(Agent a, AbstractMachine machine, AID coordinatorAgent) {
        super(a, 5000);
        this.coordinatorAgent = coordinatorAgent;
        this.machine = machine;
    }

    protected void onTick() {
        try {
            ArrayList<MachineResponse> machineResponse = machine.getMachineResponses();

            if (!machine.isStopped()) {
                ACLMessage machineStatus = new ACLMessage(ACLMessage.INFORM);
                machineStatus.addReceiver(coordinatorAgent);
                machineStatus.setOntology(CarFactoryOntology.CAR_FACTORY_ONTOLOGY);
                machineStatus.setContentObject(new MachineResponsesDto()
                        .setMachineResponses(machineResponse)
                        .setMachineType(machine.getMachineType()));
                myAgent.send(machineStatus);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
