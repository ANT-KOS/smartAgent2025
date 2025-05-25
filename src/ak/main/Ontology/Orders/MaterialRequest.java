package ak.main.Ontology.Orders;

import ak.main.Ontology.Constants.MachineResponse;
import ak.main.Ontology.Constants.Materials;
import jade.content.Concept;

public class MaterialRequest implements Concept {
    private Materials material;
    private MachineResponse response;

    public Materials getMaterial() {
        return material;
    }

    public MaterialRequest setMaterial(Materials material) {
        this.material = material;
        return this;
    }

    public MachineResponse getResponse() {
        return response;
    }

    public MaterialRequest setResponse(MachineResponse response) {
        this.response = response;
        return this;
    }
}
