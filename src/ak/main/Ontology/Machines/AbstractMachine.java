package ak.main.Ontology.Machines;

import ak.main.Ontology.Constants.MachineResponse;
import ak.main.Ontology.Constants.MachineType;
import jade.content.Concept;

import java.util.ArrayList;
import java.util.Random;

public abstract class AbstractMachine implements Concept {
    protected ArrayList<MachineResponse> responses = new ArrayList<>();
    protected boolean stopped = false;
    public abstract MachineType getMachineType();
    private int loopCounter = 0; //Machine errors should show up every 3 loops in order not to flood the system with errors.

    public ArrayList<MachineResponse> getMachineResponses() {
        ArrayList<MachineResponse> randomResponses = new ArrayList<>();
        if (loopCounter > 0) {
            loopCounter--;
        }

        if (loopCounter == 0) {
            Random random = new Random();
            randomResponses.add(responses.get(random.nextInt(responses.size() - 1, responses.size())));
            loopCounter = 0;
        }

        return randomResponses;
    }

    public void start() {
        stopped = false;
    }

    public void stop() {
        stopped = true;
    }

    public boolean isStopped() {
        return stopped;
    }

    public void repair() throws InterruptedException {
        //Simulate a repair routine
        Random random = new Random();
        int maxAttempts = 5;
        for (int i = 0; i < maxAttempts -1; i++) {
            boolean success = random.nextBoolean();
            System.out.println("Attempt " + (i + 1) + " to repair: " + this.getMachineType().getMachineName());

            if (success) {
                System.out.println("Repair Success");
                return;
            }
        }

        Thread.sleep(3000);

        System.out.println("Repair Completed after " + (maxAttempts + 1) + " attempts");
    }
}
