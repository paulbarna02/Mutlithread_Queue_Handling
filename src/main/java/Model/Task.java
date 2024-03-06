package Model;

import java.util.concurrent.atomic.AtomicInteger;

public class Task {
    private final int ID;
    private final int arrivalTime;
    private AtomicInteger serviceTime;
    private static int index = 0;

    public Task(int arrivalTime, int serviceTime)
    {
        ID = ++index;
        this.arrivalTime = arrivalTime;
        this.serviceTime = new AtomicInteger(serviceTime);
    }

    public int getID() {
        return ID;
    }

    public AtomicInteger getServiceTime() {
        return serviceTime;
    }
    public int getArrivalTime(){return arrivalTime;}
    public void decreaseServiceTime(){serviceTime.decrementAndGet();}

    public String toString()
    {
        return "(" + String.valueOf(ID) + ", " + String.valueOf(arrivalTime) + ", " + String.valueOf(serviceTime) + ")\n";
    }
}
