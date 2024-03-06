package Business;

import Business.Logger;
import Business.Scheduler;
import GUI.GenerationFrame;
import GUI.SimulationFrame;
import Model.Server;
import Model.Task;

import java.io.File;
import java.util.*;

public class SimulationManager implements Runnable{
    private static int simTime;
    private final Scheduler scheduler;
    private static ArrayList<Task> tasks;
    private final int minWaiting;
    private final int maxWaiting;
    private final int minArr;
    private final int maxArr;
    private final int numberOfClients;
    private final int numberOfServers;
    private final int maxSim;
    private final File file;
    public SimulationManager(int minArr, int maxArr, int minWaiting, int maxWaiting, int numberOfClients, int numberOfServers, int maxSim, File file)
    {
        this.scheduler = new Scheduler(numberOfServers, numberOfClients);
        this.minArr = minArr;
        this.maxArr = maxArr;
        this.minWaiting = minWaiting;
        this.maxWaiting = maxWaiting;
        this.numberOfClients = numberOfClients;
        this.numberOfServers = numberOfServers;
        this.maxSim = maxSim;
        simTime = 0;
        generateRandomTasks();
        this.file = file;
    }

    public void generateRandomTasks()
    {
        try {
            Random r = new Random();
            tasks = new ArrayList<Task>();
            for (int i = 0; i < numberOfClients; i++)
                tasks.add(new Task(r.nextInt(minArr, maxArr), r.nextInt(minWaiting, maxWaiting)));
        }catch(Exception e){

        }
    }

    public static int getSimTime()
    {
        return simTime;
    }

    public static String tasksToString()
    {
        String s = "";

        for(Task t: tasks)
            if(t.getArrivalTime() > simTime)
                s += t.toString();

        return s;
    }

    public String toString()
    {
        String s = "Time " + String.valueOf(simTime) + "\n" + "Waiting clients:";
        for(Task t: tasks)
            if(simTime < t.getArrivalTime())
                s += "(" + String.valueOf(t.getID()) + ", " + String.valueOf(t.getArrivalTime())
                        + ", " + String.valueOf(t.getServiceTime()) + "),";
        s += "\n";

        for(Server a: scheduler.getServers()) {
            s += "Queue " + String.valueOf(a.getID()) + ": ";// + a.getWaitingPeriod();
            if(a.getCurrTask() != null) {
                s += "(" + String.valueOf(a.getCurrTask().getID()) + ", " + String.valueOf(a.getCurrTask().getArrivalTime())
                        + ", " + String.valueOf(a.getCurrTask().getServiceTime()) + "),";

                for (Task t : a.getTasks())
                    s += "(" + String.valueOf(t.getID()) + ", " + String.valueOf(t.getArrivalTime())
                            + ", " + String.valueOf(t.getServiceTime()) + "),";
            }
            else
                s += "closed";
            s += "\n";
        }

        return s;
    }

    public float computeAverageServiceTime()
    {
        float average = 0;
        for(Task t: tasks)
            average += t.getServiceTime().get();
        return average / tasks.size();
    }

    @Override
    public void run() {
        simTime = 0;
        boolean ok = true;
        float averageWaitingTime = 0;
        float steps = 0;
        float averageServiceTime = computeAverageServiceTime();
        int currentPeak = 0;
        int peakHour = 0;
        int currentNoOfClients = 0;
        SimulationFrame.displaySimTime();
        Logger logger = new Logger(file);
        while(simTime <= maxSim && ok) {
            ok = false;

            SimulationFrame.displayTasks();

            for (Task t : tasks) {
                if (simTime == t.getArrivalTime())
                    scheduler.dispatchTask(t);
                if(simTime < t.getArrivalTime())
                    ok = true;
            }

            logger.write(this.toString());
            logger.write("\n");
            currentNoOfClients = 0;

            for(Server s: scheduler.getServers()){
                if(!s.getTasks().isEmpty() || s.getCurrTask() != null)
                    ok = true;
                currentNoOfClients += s.getTasks().size();
                if(s.getCurrTask() != null) {
                    s.getCurrTask().decreaseServiceTime();
                    currentNoOfClients++;
                    if (s.getCurrTask().getServiceTime().get() == 0)
                        s.setCurrTask(null);
                    SimulationFrame.displayCurrentTask(s.getCurrTask(), s.getID());
                }
                if(s.getWaitingPeriod().get() != 0) {
                    averageWaitingTime += s.getWaitingPeriod().get();
                    steps++;
                }
                SimulationFrame.displayDispatched(s.getTasks(), s.getID());
            }

            if(currentNoOfClients > currentPeak)
            {
                currentPeak = currentNoOfClients;
                peakHour = simTime;
            }

            simTime++;
            if(simTime < maxSim)
                SimulationFrame.displaySimTime();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        for(Server s: scheduler.getServers())
            s.setFalse();
        logger.write("Average waiting time: " + String.valueOf(averageWaitingTime / steps) + "\n");
        logger.write("Average service time: " + String.valueOf(averageServiceTime) + "\n");
        logger.write("Peak hour: " + String.valueOf(peakHour) + " (" + String.valueOf(currentPeak) + " clients)");
        logger.closeFile();
        GenerationFrame.close();
        GenerationFrame.setEndLabel();
    }
}
