package GUI;

import Business.SimulationManager;
import Model.Task;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ArrayBlockingQueue;

public class SimulationFrame extends JFrame {

    private static JPanel panel;
    private static ArrayBlockingQueue<Task> queue;
    private static int queues;
    private static JLabel[] queueLabel;
    private static JLabel simTime;
    private static  JTextArea tasks;
    private static JLabel[] currTask;

    SimulationFrame(int queues)
    {
        this.setTitle("Queues Management Application");
        this.setBounds(300, 100, 1000, 700);
        this.setResizable(false);

        SimulationFrame.queues = queues;
        queueLabel = new JLabel[queues];
        tasks = new JTextArea();
        currTask = new JLabel[queues];

        panel = new JPanel(null);
        this.add(panel);
        panel.setBackground(new Color(255, 230, 180));

        JLabel[] queueLabels = new JLabel[queues];

        simTime = new JLabel();

        JLabel t = new JLabel("Clients:");
        t.setFont(new Font(Font.SERIF, Font.PLAIN, 25));
        t.setBackground(new Color(255, 230, 180));
        panel.add(t);
        t.setBounds(850, 0, 150, 30);


        for(int i = 0; i < queues; i++) {
            queueLabels[i] = new JLabel("Q" + String.valueOf(i + 1));
            queueLabels[i].setFont(new Font(Font.SERIF, Font.PLAIN, 25));
            panel.add(queueLabels[i]);
            queueLabels[i].setBounds(50, 30 + i * 600 / queues, 50, 30);
            queueLabel[i] = new JLabel();
            queueLabel[i].setFont(new Font(Font.SERIF, Font.PLAIN, 25));
            panel.add(queueLabel[i]);
            currTask[i] = new JLabel();
            currTask[i].setBounds(50,  30 + i * 600 / queues, 50, 30);
            currTask[i] = new JLabel();
            currTask[i].setFont(new Font(Font.SERIF, Font.PLAIN, 25));
            panel.add(currTask[i]);
            currTask[i].setBounds(100,  30 + i * 600 / queues, 149, 30);
        }

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

    }

    public static void displayTasks()
    {
        tasks.setText(SimulationManager.tasksToString());
        tasks.setFont(new Font(Font.SERIF, Font.PLAIN, 25));
        tasks.setBackground(new Color(255, 230, 180));
        panel.add(tasks);
        tasks.setBounds(850, 30, 250, 1000);
    }

    public static void displayDispatched(ArrayBlockingQueue<Task> q, int y)
    {
        Task task;
        queue = new ArrayBlockingQueue<Task>(q.size() + 1);
        queue.addAll(q);
        String s = "";

        queueLabel[y - 1].setFont(new Font(Font.SERIF, Font.PLAIN, 25));
        queueLabel[y - 1].setBackground(new Color(255, 230, 180));
        panel.add(queueLabel[y - 1]);
        queueLabel[y - 1].setBounds(250, 30 + (y - 1) * 600 / queues, 500, 30);

        //System.out.println("da");

        while(!queue.isEmpty()) {
            try {
                task = queue.take();
                s += task.toString();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        queueLabel[y - 1].setText(s);
    }

    public static void displaySimTime()
    {
        simTime.setText("Simulation time: " + String.valueOf(SimulationManager.getSimTime()));
        simTime.setFont(new Font(Font.SERIF, Font.PLAIN, 25));
        simTime.setBackground(new Color(255, 230, 180));
        panel.add(simTime);
        simTime.setBounds(50, 0, 500, 30);
    }

    public static void displayCurrentTask(Task t, int y)
    {
        if(t != null) {
            currTask[y - 1].setText(t.toString());
        }
        else
            currTask[y - 1].setText("");
    }

}
