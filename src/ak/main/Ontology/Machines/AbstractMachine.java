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
    private int loopCounter = 0;

    public ArrayList<MachineResponse> getMachineResponses() {
        ArrayList<MachineResponse> randomResponses = new ArrayList<>();
        if (loopCounter == 0 && !stopped) {

            Random random = new Random();
            randomResponses.add(responses.get(1));
            loopCounter = 0;
        }

        if (stopped) {
            ArrayList<MachineResponse> stoppedResponse = new ArrayList<>();
            stoppedResponse.add(MachineResponse.MACHINE_STOPPED);
            return stoppedResponse;
        }
        loopCounter--;

        return randomResponses;
    }

    public void start() {
        stopped = false;
        responses.clear();
    }

    public void stop() {
        stopped = true;
    }

    public boolean isStopped() {
        return stopped;
    }

    public void repair() throws InterruptedException {
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
        wait(10000);

        System.out.println("Repair Completed after " + (maxAttempts + 1) + " attempts");
    }
}
