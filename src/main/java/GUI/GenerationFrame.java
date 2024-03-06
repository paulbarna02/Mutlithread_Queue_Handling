package GUI;

import Business.SimulationManager;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class GenerationFrame extends JFrame {

    private int tasks;
    private int queues;
    private int minArr;
    private int maxArr;
    private int minService;
    private int maxService;
    private int simTime;
    private boolean exeption = false;
    private static JPanel panel;
    private static SimulationFrame simulationFrame;

    public void setExeption(){exeption = true;}
    public GenerationFrame(){
        this.setTitle("Queues Management Application");
        this.setBounds(300, 100, 1000, 700);
        this.setResizable(false);

        panel = new JPanel(null);
        this.add(panel);
        panel.setBackground(new Color(255, 230, 180));

        JLabel title = new JLabel("Queues Management Application");
        title.setFont(new Font(Font.SERIF, Font.PLAIN, 50));
        title.setBounds(200, 0, 1000, 100);
        panel.add(title);

        JLabel tasks = new JLabel("Number of clients:");
        tasks.setFont(new Font(Font.SERIF, Font.PLAIN, 25));
        panel.add(tasks);
        tasks.setBounds(50, 150, 300, 50);

        JTextField tasksTF = new JTextField();
        tasksTF.setFont(new Font(Font.SERIF, Font.PLAIN, 25));
        panel.add(tasksTF);
        tasksTF.setBounds(300, 150, 50, 50);

        JLabel queues = new JLabel("Number of queues:");
        queues.setFont(new Font(Font.SERIF, Font.PLAIN, 25));
        panel.add(queues);
        queues.setBounds(50, 210, 300, 50);

        JTextField queuesTF = new JTextField();
        queuesTF.setFont(new Font(Font.SERIF, Font.PLAIN, 25));
        panel.add(queuesTF);
        queuesTF.setBounds(300, 210, 50, 50);

        JLabel minWaiting = new JLabel("Minimum waiting time:");
        minWaiting.setFont(new Font(Font.SERIF, Font.PLAIN, 25));
        panel.add(minWaiting);
        minWaiting.setBounds(50, 270, 300, 50);

        JTextField minWaitingTF = new JTextField();
        minWaitingTF.setFont(new Font(Font.SERIF, Font.PLAIN, 25));
        panel.add(minWaitingTF);
        minWaitingTF.setBounds(300, 270, 50, 50);

        JLabel maxWaiting = new JLabel("Maximum waiting time:");
        maxWaiting.setFont(new Font(Font.SERIF, Font.PLAIN, 25));
        panel.add(maxWaiting);
        maxWaiting.setBounds(50, 330, 300, 50);

        JTextField maxWaitingTF = new JTextField();
        maxWaitingTF.setFont(new Font(Font.SERIF, Font.PLAIN, 25));
        panel.add(maxWaitingTF);
        maxWaitingTF.setBounds(300, 330, 50, 50);

        JLabel minArr = new JLabel("Minimum arrival time:");
        minArr.setFont(new Font(Font.SERIF, Font.PLAIN, 25));
        panel.add(minArr);
        minArr.setBounds(50, 390, 300, 50);

        JTextField minArrTF = new JTextField();
        minArrTF.setFont(new Font(Font.SERIF, Font.PLAIN, 25));
        panel.add(minArrTF);
        minArrTF.setBounds(300, 390, 50, 50);

        JLabel maxArr = new JLabel("Maximum arrival time:");
        maxArr.setFont(new Font(Font.SERIF, Font.PLAIN, 25));
        panel.add(maxArr);
        maxArr.setBounds(50, 450, 300, 50);

        JTextField maxArrTF = new JTextField();
        maxArrTF.setFont(new Font(Font.SERIF, Font.PLAIN, 25));
        panel.add(maxArrTF);
        maxArrTF.setBounds(300, 450, 50, 50);

        JLabel simTime = new JLabel("Simulation time:");
        simTime.setFont(new Font(Font.SERIF, Font.PLAIN, 25));
        panel.add(simTime);
        simTime.setBounds(50, 510, 300, 50);

        JTextField simTimeTF = new JTextField();
        simTimeTF.setFont(new Font(Font.SERIF, Font.PLAIN, 25));
        panel.add(simTimeTF);
        simTimeTF.setBounds(300, 510, 50, 50);

        JButton btn = new JButton("Go");
        btn.setFont(new Font(Font.SERIF, Font.PLAIN, 25));
        panel.add(btn);
        btn.setBounds(50, 570, 350, 50);

        btn.addActionListener(e -> {

            if(tasksTF.getText().equals("") || queuesTF.getText().equals("") || minArrTF.getText().equals("")
                    || maxArrTF.getText().equals("") || minWaitingTF.getText().equals("") || maxWaitingTF.getText().equals("")
                    || simTimeTF.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "No input", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(Integer.parseInt(minArrTF.getText()) >= Integer.parseInt(maxArrTF.getText()) || Integer.parseInt(minWaitingTF.getText()) >= Integer.parseInt(maxWaitingTF.getText()))
            {
                JOptionPane.showMessageDialog(this, "Incorrect data", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            this.tasks = Integer.parseInt(tasksTF.getText());
            this.queues = Integer.parseInt(queuesTF.getText());
            this.minArr = Integer.parseInt(minArrTF.getText());
            this.maxArr = Integer.parseInt(maxArrTF.getText());
            this.minService = Integer.parseInt(minWaitingTF.getText());
            this.maxService = Integer.parseInt(maxWaitingTF.getText());
            this.simTime = Integer.parseInt(simTimeTF.getText());

            tasksTF.setVisible(false);
            JLabel tasksLabel = new JLabel(String.valueOf(this.tasks));
            tasksLabel.setFont(new Font(Font.SERIF, Font.PLAIN, 25));
            panel.add(tasksLabel);
            tasksLabel.setBounds(300, 150, 50, 50);

            queuesTF.setVisible(false);
            JLabel queuesLabel = new JLabel(String.valueOf(this.queues));
            queuesLabel.setFont(new Font(Font.SERIF, Font.PLAIN, 25));
            panel.add(queuesLabel);
            queuesLabel.setBounds(300, 210, 50, 50);

            minArrTF.setVisible(false);
            JLabel minArrLabel = new JLabel(String.valueOf(this.minArr));
            minArrLabel.setFont(new Font(Font.SERIF, Font.PLAIN, 25));
            panel.add(minArrLabel);
            minArrLabel.setBounds(300, 390, 50, 50);

            minWaitingTF.setVisible(false);
            JLabel minWaitingLabel = new JLabel(String.valueOf(this.minService));
            minWaitingLabel.setFont(new Font(Font.SERIF, Font.PLAIN, 25));
            panel.add(minWaitingLabel);
            minWaitingLabel.setBounds(300, 270, 50, 50);

            maxWaitingTF.setVisible(false);
            JLabel maxWaitingLabel = new JLabel(String.valueOf(this.maxService));
            maxWaitingLabel.setFont(new Font(Font.SERIF, Font.PLAIN, 25));
            panel.add(maxWaitingLabel);
            maxWaitingLabel.setBounds(300, 330, 50, 50);

            maxArrTF.setVisible(false);
            JLabel maxArrLabel = new JLabel(String.valueOf(this.maxArr));
            maxArrLabel.setFont(new Font(Font.SERIF, Font.PLAIN, 25));
            panel.add(maxArrLabel);
            maxArrLabel.setBounds(300, 450, 50, 50);

            simTimeTF.setVisible(false);
            JLabel simTimeLabel = new JLabel(String.valueOf(this.simTime));
            simTimeLabel.setFont(new Font(Font.SERIF, Font.PLAIN, 25));
            panel.add(simTimeLabel);
            simTimeLabel.setBounds(300, 510, 50, 50);

            btn.setVisible(false);

            JLabel wait = new JLabel("Running simultation...");
            wait.setBackground(new Color(255, 230, 180));
            wait.setFont(new Font(Font.SERIF, Font.PLAIN, 35));
            panel.add(wait);
            wait.setBounds(500, 300, 500, 100);

            simulationFrame = new SimulationFrame(this.queues);
            SimulationManager simulationManager = new SimulationManager(this.minArr, this.maxArr, this.minService, this.maxService, this.tasks, this.queues, this.simTime, new File("log.txt"));
            Thread thread = new Thread(simulationManager);
            thread.start();

        });

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

    }

    public static void close()
    {
        simulationFrame.setVisible(false);
        simulationFrame.dispose();
    }

    public static void setEndLabel()
    {
        JTextArea wait = new JTextArea("Simulation complete\n   Check log file");
        wait.setBackground(new Color(255, 230, 180));
        wait.setFont(new Font(Font.SERIF, Font.PLAIN, 35));
        panel.add(wait);
        wait.setBounds(500, 300, 500, 100);
    }

}
