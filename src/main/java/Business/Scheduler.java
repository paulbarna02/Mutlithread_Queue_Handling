package Business;

import Model.Server;
import Model.Task;

import java.util.ArrayList;

public class Scheduler {
    private ArrayList<Server> servers;
    private int maxNoServers;
    private int maxTasksPerServer;

    private Thread[] thread;

    public Scheduler(int maxNoServers, int maxTasksPerServer)
    {
        this.maxNoServers = maxNoServers;
        this.maxTasksPerServer =  maxTasksPerServer;
        this.servers = new ArrayList<>(maxNoServers);
        this.thread = new Thread[maxNoServers];

        for(int i = 0; i < maxNoServers; i++) {
            Server newServer = new Server(maxTasksPerServer);
            this.servers.add(newServer);
        }

        for(Server s: servers){
            thread[s.getID() - 1] = new Thread(s);
            thread[s.getID() - 1].start();
        }
    }

    public ArrayList<Server> getServers() {
        return servers;
    }

    public void dispatchTask(Task t)
    {
        Strategy.addTask(servers, t);
    }

}
