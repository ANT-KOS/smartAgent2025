package ak.main.Ontology.Machines;

import ak.main.Ontology.Constants.MachineResponse;
import ak.main.Ontology.Constants.MachineType;
import jade.content.Concept;

import java.util.ArrayList;
import java.util.Random;

public abstract class AbstractMachine implements Concept {
    private int maxAttempts = 5;
    protected ArrayList<MachineResponse> responses = new ArrayList<>();
    protected boolean stopped = false;
    public abstract MachineType getMachineType();

    public ArrayList<MachineResponse> getMachineResponses() {
        double selectionProbability = 0.2;
        Random random = new Random();

        ArrayList<MachineResponse> randomResponses = new ArrayList<>();
        for (MachineResponse r : responses) {
            if (random.nextDouble() < selectionProbability && !r.equals(MachineResponse.MACHINE_STOPPED)) {
                randomResponses.add(r);
            }
        }

        if (stopped) {
            ArrayList<MachineResponse> stoppedResponse = new ArrayList<>();
            stoppedResponse.add(MachineResponse.MACHINE_STOPPED);
            return stoppedResponse;
        }

        return randomResponses;
    }

    public void start() {
        stopped = false;
        responses.clear();
    }

    public void stop() {
        stopped = true;
    }

    public boolean repair() {
        Random random = new Random();
        for (int i = 0; i < maxAttempts-1; i++) {
            boolean success = random.nextBoolean();
            System.out.println("Attempt " + (i + 1) + " to repair: " + this.getMachineType().getMachineName());

            if (success) {
                System.out.println("Repair Success");
                return true;
            }
        }

        System.out.println("Repair Completed after " + (maxAttempts + 1) + " attempts");
        return true;
    }
}
