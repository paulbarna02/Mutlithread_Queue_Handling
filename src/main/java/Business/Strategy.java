package Business;

import Model.Server;
import Model.Task;

import java.util.ArrayList;

public class Strategy {
    static public void addTask(ArrayList<Server> servers, Task task)
    {
        int minWaitingPeriod = servers.get(0).getWaitingPeriod().get();
        Server bestServer = servers.get(0);
        for(Server s: servers)
        {
            if(minWaitingPeriod >= s.getWaitingPeriod().get())
            {
                minWaitingPeriod = s.getWaitingPeriod().get();
                bestServer = s;
            }
        }

        bestServer.addTask(task);
    }
}
