package ak.main.Ontology.Orders;

import ak.main.Ontology.Constants.Materials;
import jade.content.Concept;

public class MaterialOrder implements Concept {
    private Materials material;
    private int quantity;

    public Materials getMaterial() {
        return material;
    }

    public MaterialOrder setMaterial(Materials material) {
        this.material = material;
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public MaterialOrder setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }
}
