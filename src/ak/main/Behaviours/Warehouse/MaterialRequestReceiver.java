package ak.main.Behaviours.Warehouse;

import ak.main.Agents.Constants.AgentNames;
import ak.main.Ontology.Constants.MachineResponse;
import ak.main.Ontology.Constants.Materials;
import ak.main.Ontology.Orders.MaterialOrder;
import ak.main.Ontology.Orders.MaterialRequest;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

import java.io.IOException;

//This behaviour handles material requests
public class MaterialRequestReceiver extends CyclicBehaviour {
    public MaterialRequestReceiver(Agent a) {
        super(a);
    }

    public void action() {
        MessageTemplate messageTemplate = MessageTemplate.and(
                MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
                MessageTemplate.MatchReceiver(new AID[]{new AID(AgentNames.WAREHOUSE_AGENT.getAgentName(), AID.ISLOCALNAME)})
        );

        ACLMessage msg = myAgent.receive(messageTemplate);
        if (msg != null) {

            try{
                MaterialRequest request = (MaterialRequest) msg.getContentObject();
                System.out.println("Order received for: " + request.getMaterial());
                MaterialOrder order = new MaterialOrder();

                switch (request.getResponse()) {
                    case MachineResponse.PAINT_LEVEL_LOW -> order.setMaterial(Materials.PAINT).setQuantity(3000);
                    case MachineResponse.NO_PAINT -> order.setMaterial(Materials.PAINT).setQuantity(4500);
                    case MachineResponse.NO_PLASTIC -> order.setMaterial(Materials.PLASTIC).setQuantity(6000);
                    case MachineResponse.PLASTIC_LEVEL_LOW -> order.setMaterial(Materials.PLASTIC).setQuantity(4000);
                    case MachineResponse.NO_ALUMINIUM -> order.setMaterial(Materials.ALUMINUM).setQuantity(10000);
                    case MachineResponse.ALUMINIUM_LEVEL_LOW -> order.setMaterial(Materials.ALUMINUM).setQuantity(9000);
                    case MachineResponse.PACKAGING_MATERIAL_LOW -> order.setMaterial(Materials.PACKAGING).setQuantity(8000);
                    case MachineResponse.PACKAGING_MATERIAL_EMPTY -> order.setMaterial(Materials.PACKAGING).setQuantity(10000);
                }

                ACLMessage reply = msg.createReply();
                reply.setPerformative(ACLMessage.INFORM);
                reply.setContentObject(order);
                myAgent.send(reply);
            } catch (UnreadableException | IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            block();
        }
    }
}
