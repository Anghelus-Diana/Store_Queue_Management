package BusinessLogic;

import Model.Server;
import Model.Task;

import java.util.List;

public class ConcreteStrategyQueue implements Strategy {

    @Override
    public void addTask(List<Server> servers, Task t) {
        if(servers.size()==0)
            return;
        Server minQueueServer = servers.get(0);
        for(Server server:servers){
            if(server.size()< minQueueServer.size())
                minQueueServer = server;
        }
        minQueueServer.addTask(t);
    }
}