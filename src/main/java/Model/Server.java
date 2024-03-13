package Model;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {
    private int id;
    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;
    private int stepMillis;
    private int timeLimit;

    public Server(int id, int maxNumberOfClients, int stepMillis, int timeLimit) {
        tasks = new ArrayBlockingQueue<>(maxNumberOfClients);
        this.stepMillis = stepMillis;
        this.timeLimit = timeLimit;
        this.id = id;
        waitingPeriod = new AtomicInteger(0);
    }

    public void addTask(Task newTask) {
        waitingPeriod.addAndGet(newTask.getServiceTime());
        tasks.add(newTask);
    }

    //pt procesat fiecare coada din servere
    public void run() {
        for (int currTime = 0; currTime < timeLimit; currTime++) {
            Task task = tasks.peek();
            if (task == null) {
                try {
                    Thread.sleep(stepMillis);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else {
                int serviceTime = task.getServiceTime();
                for (int i = 0; i < serviceTime; i++) {
                    try {
                        Thread.sleep(stepMillis);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    waitingPeriod.decrementAndGet();
                    task.setServiceTime(task.getServiceTime() - 1);
                }
                tasks.remove();
            }
            //System.out.println("" + id + " " + currTime);
        }
    }

    public int size() {
        return tasks.size();
    }

    public int time() {
        return waitingPeriod.get();
    }

    @Override
    public String toString() {
        String s = "Queue " + id + ":  ";
        if(tasks.size()==0)
            s+="Empty";
        else{
            for (Task task : tasks)
                s += task + ", ";
            s = s.substring(0, s.length() - 2);
        }

        return s;
    }
}
