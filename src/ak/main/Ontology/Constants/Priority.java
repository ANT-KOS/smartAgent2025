package ak.main.Ontology.Constants;

public enum Priority {
    LOW (1),
    MEDIUM (2),
    HIGH (3);

    private final int order;

    Priority(int order) {
        this.order = order;
    }

    public int getOrder() {
        return order;
    }
}
