package BusinessLogic;

import Model.Server;
import Model.Task;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {
    List<Server> servers = new ArrayList<>();
    int maxNoServers;
    int maxTasksPerServer;
    int stepMillis;
    private Strategy strategy;


    //In scheduler dam drumul la toate serverele
    public Scheduler(int maxNoServers, int maxTasksPerServer, SelectionPolicy policy, int stepMillis, int timeLimit) {
        this.stepMillis = stepMillis;
        changeStrategy(policy);
        this.maxNoServers = maxNoServers;
        this.maxTasksPerServer = maxTasksPerServer;

        for (int i = 0; i < maxNoServers; i++) {
            Server server = new Server(i + 1, maxTasksPerServer, stepMillis, timeLimit);
            servers.add(server);
            (new Thread(server)).start();
        }
    }

    //Schimbam strategia aici
    public void changeStrategy(SelectionPolicy policy) {
        if (policy == SelectionPolicy.SHORTEST_QUEUE) {
            strategy = new ConcreteStrategyQueue();
        }

        if (policy == SelectionPolicy.SHORTEST_TIME) {
            strategy = new ConcreteStrategyTime();
        }
    }

    //Apeleaza functia de schimbare a strategiei
    public void dispatchTask(Task task) {
        strategy.addTask(servers, task);
    }

    public List<Server> getServers() {
        return servers;
    }


    //printeaza cozile aflate in fiecare server
    public String print() {
        String s = "";
        for (Server server : servers) {
            s += server + "\n";
        }

        return s;
    }
}
