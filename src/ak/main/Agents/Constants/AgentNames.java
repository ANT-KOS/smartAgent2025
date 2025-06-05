package ak.main.Agents.Constants;

public enum AgentNames {
    COORDINATOR_AGENT("CoordinatorAgent"),
    MACHINE_AGENT("MachineAgent"),
    MAINTENANCE_AGENT("MaintenanceAgent"),
    WAREHOUSE_AGENT("WarehouseAgent");

    private final String agentName;

    AgentNames(String agentName) {
        this.agentName = agentName;
    }

    public String getAgentName(String agentSubname) {
        return agentName + "_" + agentSubname;
    }

    public String getAgentName() {
        return agentName;
    }
}
