package ak.main.Behaviours.Coordinator;

import ak.main.Agents.CoordinatorAgent;
import ak.main.Ontology.CarFactoryOntology;
import ak.main.Ontology.Constants.MachineResponse;
import ak.main.Ontology.Constants.MachineStatus;
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

//This behaviour handles the proposals or refusals of the CFP messages between the coordinator and the maintenance agents.
public class MaintenanceRepairContractNetInitiator extends ContractNetInitiator {
    private MachineType machineType;
    private MachineResponse machineResponse;
    private ACLMessage repairCfp;
    private MachineStatusInspector inspector;

    public MaintenanceRepairContractNetInitiator(Agent agent,
                                                 ACLMessage repairCfp,
                                                 MachineType machineType,
                                                 MachineResponse machineResponse,
                                                 MachineStatusInspector machineStatusInspector) {
        super(agent, repairCfp);
        this.machineType = machineType;
        this.machineResponse = machineResponse;
        this.repairCfp = repairCfp;
        this.inspector = machineStatusInspector;
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
                //If the maintenance agent we asked for a proposal is not available then we will check the
                //next available agent.
                sendNewCfpToNextAgent(availableMaintenanceAgents);
            } else {
                block();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void handleInform(ACLMessage inform) {
        ((CoordinatorAgent) myAgent).changeMachineStatus(machineType, MachineStatus.OPERATING);
        try {
            inspector.processQueuedRequests();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void handleFailure(ACLMessage failure) {
        try {
            inspector.processQueuedRequests();
        } catch (IOException e) {
            throw new RuntimeException(e);
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
        AID nextAgent = availableAgents.getFirst();
        ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
        cfp.addReceiver(nextAgent);
        cfp.setOntology(CarFactoryOntology.CAR_FACTORY_ONTOLOGY);
        cfp.setLanguage(FIPANames.ContentLanguage.FIPA_SL);
        cfp.setContent("Requesting repair for: " + machineType);

        System.out.println("Sending new CFP to: " + nextAgent.getLocalName());
        myAgent.send(cfp);
    }
}
