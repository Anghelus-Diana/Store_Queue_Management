package BusinessLogic;

import GUI.SimulationFrame;
import GUI.SimulationFrame2;
import Model.Server;
import Model.Task;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SimulationManager implements Runnable {

    private int timeLimit;
    private int numbersOfClients;
    private int numberOfServers;
    private int minArrivalTime;
    private int maxArrivalTime;
    private int minProcessingTime;
    private int maxProcessingTime;
    private SelectionPolicy selectionPolicy;
    private int stepMillis;
    private Scheduler scheduler;
    private SimulationFrame frame;
    private List<Task> tasks = new ArrayList<>();

    public  SimulationManager()
    {
        SimulationFrame f=new SimulationFrame(this);
    }

    public void initialize(int timeLimit, int numbersOfClients, int numberOfServers, int minArrivalTime, int maxArrivalTime, int minProcessingTime, int maxProcessingTime, SelectionPolicy selectionPolicy, int stepMillis) {
        this.timeLimit = timeLimit;
        this.numbersOfClients = numbersOfClients;
        this.numberOfServers = numberOfServers;
        this.minArrivalTime = minArrivalTime;
        this.maxArrivalTime = maxArrivalTime;
        this.minProcessingTime = minProcessingTime;
        this.maxProcessingTime = maxProcessingTime;
        this.selectionPolicy = selectionPolicy;
        this.stepMillis = stepMillis;


        generateNRandomTasks();
        System.out.println(tasks);
        scheduler = new Scheduler(numberOfServers, numbersOfClients, selectionPolicy, stepMillis, timeLimit);
    }

    private void generateNRandomTasks() {
        int serviceTime;
        int arrivalTime;
        Random random = new Random(0);
        for (int i = 1; i <= numbersOfClients; i++) {
            serviceTime = random.nextInt(maxProcessingTime - minProcessingTime + 1) + minProcessingTime;
            arrivalTime = random.nextInt(maxArrivalTime - minArrivalTime + 1) + minArrivalTime;
            tasks.add(new Task(i, arrivalTime, serviceTime));

        }
        Collections.sort(tasks);

    }

    public void run() {
        SimulationFrame2 frame2=new SimulationFrame2();

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("log.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for(int currTime = 0; currTime < timeLimit; currTime++) {
            String queues = scheduler.print();
            try {
                fileWriter.write("Time " + currTime + "\nRemaining tasks: " + tasks + "\n");
                fileWriter.write(queues + "\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Time " + currTime + "\nRemaining tasks: " + tasks);
            System.out.println(queues);
            frame2.printQueue(scheduler, tasks, currTime);
            List<Task> toDelete = new ArrayList<>();
            for (Task task : tasks) {
                if(task.getArrivalTime()<=currTime){
                    scheduler.dispatchTask(task);
                    toDelete.add(task);
                }
            }
            tasks.removeAll(toDelete);
            try {
                Thread.sleep(stepMillis);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
