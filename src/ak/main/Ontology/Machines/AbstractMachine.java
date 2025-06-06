package ak.main.Ontology.Machines;

import ak.main.Ontology.Constants.MachineResponse;
import ak.main.Ontology.Constants.MachineType;
import jade.content.Concept;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
        int repairDurationInMS = 3000;
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.schedule(() -> {
            System.out.println("Repair duration " +  repairDurationInMS + " ms");
        },repairDurationInMS, TimeUnit.MILLISECONDS);
    }
}
