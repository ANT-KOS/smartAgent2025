package ak.main.Behaviours.Coordinator;

import ak.main.Agents.CoordinatorAgent;
import ak.main.Ontology.CarFactoryOntology;
import ak.main.Ontology.Constants.MachineResponse;
import ak.main.Ontology.Constants.MachineType;
import ak.main.Ontology.Dto.MaintenanceRequestDto;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.proto.ContractNetInitiator;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

public class MaintenanceRepairContractNetInitiator extends ContractNetInitiator {
    private MachineType machineType;
    private MachineResponse machineResponse;

    public MaintenanceRepairContractNetInitiator(
            Agent agent,
            ACLMessage repairCfp,
            MachineType machineType,
            MachineResponse machineResponse) {
        super(agent, repairCfp);
        this.machineType = machineType;
        this.machineResponse = machineResponse;
    }

    protected void handlePropose(ACLMessage propose, Vector proposals) {
        System.out.println("Received PROPOSE from: " + propose.getSender().getLocalName());

        ACLMessage accept = propose.createReply();
        accept.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
        accept.setContent("Accepted maintenance proposal");
        proposals.add(accept);
        System.out.println("Accepted maintenance proposal from: " + propose.getSender().getLocalName());

        try {
            sendRepairRequest(propose.getSender());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void handleRefuse(ACLMessage refuse) {
        System.out.println("Received REFUSE from: " + refuse.getSender().getLocalName());
        ((CoordinatorAgent) myAgent).markMaintenanceAgentAsUnavailable(refuse.getSender());

        try {
            List<AID> availableMaintenanceAgents = ((CoordinatorAgent) myAgent).getAvailableMaintenanceAgents();

            if (!availableMaintenanceAgents.isEmpty()) {
                sendNewCfpToNextAgent(availableMaintenanceAgents);
            } else {
                System.out.println("No maintenance agents are available at the moment. Waiting...");
                block();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void sendRepairRequest(AID maintenanceAgent) throws IOException {
        ACLMessage repairRequest = new ACLMessage(ACLMessage.REQUEST);
        repairRequest.addReceiver(maintenanceAgent);
        repairRequest.setOntology(CarFactoryOntology.CAR_FACTORY_ONTOLOGY);
        repairRequest.setLanguage(FIPANames.ContentLanguage.FIPA_SL);
        repairRequest.setContentObject(new MaintenanceRequestDto()
                .setMachineType(machineType)
                .setMachineResponse(machineResponse));
        repairRequest.setConversationId("repair-" + machineType.getMachineName() + "-" + System.currentTimeMillis());
        repairRequest.setReplyWith("repair-request-" + System.currentTimeMillis());

        myAgent.send(repairRequest);
        System.out.println("Sent repair request to: " + maintenanceAgent.getLocalName());
    }

    private void sendNewCfpToNextAgent(List<AID> availableAgents) {
        AID nextAgent = availableAgents.getFirst(); // In a real scenario, you could implement a round-robin or another selection strategy
        ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
        cfp.addReceiver(nextAgent);
        cfp.setOntology(CarFactoryOntology.CAR_FACTORY_ONTOLOGY);
        cfp.setLanguage(FIPANames.ContentLanguage.FIPA_SL);
        cfp.setContent("Requesting repair for: " + machineType);

        System.out.println("Sending new CFP to: " + nextAgent.getLocalName());
        myAgent.send(cfp);
    }
}
