package BusinessLogic;

import Model.Server;
import Model.Task;

import java.util.List;

public class ConcreteStrategyTime implements Strategy {

    @Override
    public void addTask(List<Server> servers, Task t) {
        if(servers.size()==0)
            return;
        Server minTimeServer = servers.get(0);
        for(Server server:servers){
            if(server.time()< minTimeServer.time())
                minTimeServer = server;
        }
        minTimeServer.addTask(t);
    }
}
