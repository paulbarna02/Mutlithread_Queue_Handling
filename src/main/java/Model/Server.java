package Model;

import Model.Task;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable{
    private ArrayBlockingQueue<Task> tasks;
    private Task currTask;
    private AtomicInteger waitingPeriod;
    private final int ID;
    private static int index = 0;
    boolean ok;

    public Server(int capacity)
    {
        tasks = new ArrayBlockingQueue<Task>(capacity);
        waitingPeriod = new AtomicInteger(0);
        ID = ++index;
    }

    public int getID()
    {
        return this.ID;
    }

    public ArrayBlockingQueue<Task> getTasks()
    {
        return this.tasks;
    }

    public void addTask(Task newTask)
    {
        tasks.add(newTask);
        waitingPeriod.addAndGet(newTask.getServiceTime().get());
    }

    public Task getCurrTask(){return currTask;}

    public AtomicInteger getWaitingPeriod()
    {
        return this.waitingPeriod;
    }

    public void setCurrTask(Task currTask) {
        this.currTask = currTask;
    }

    public void setFalse(){ok = false;}

    @Override
    public void run() {
        ok = true;
        while(ok) {
            try {
                if(!tasks.isEmpty() || currTask != null) {
                    if(currTask == null || currTask.getServiceTime().get() == 0) {
                        currTask = tasks.take();
                    }

                    Thread.sleep(1000);

                    waitingPeriod.decrementAndGet();
                }
            } catch (Exception e) {
                ok = false;
            }
        }
    }
}
