package GUI;

import BusinessLogic.SelectionPolicy;
import BusinessLogic.SimulationManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.jar.JarEntry;

public class SimulationFrame extends JFrame {
    JFrame frame = new JFrame();
    private JLabel title = new JLabel("Queue Management System");

    private JLabel timeLimitText = new JLabel("Time Limit");
    private JTextField timeLimit = new JTextField(10);
    private JLabel numberOfClientsText = new JLabel("Number of clients");
    private JTextField numberOfClients = new JTextField(10);

    private JLabel numberOfQueuesText = new JLabel("Number of queues");

    private JTextField numberOfQueues = new JTextField(10);
    private JLabel arrivalTimeText = new JLabel("Arrivat time interval");
    private JTextField arrivalTime1 = new JTextField(10);
    private JTextField arrivalTime2 = new JTextField(10);
    private JLabel serviceTimeIntervalText = new JLabel("Service time interval");
    private JTextField serviceTime1 = new JTextField(10);
    private JTextField sericeTime2 = new JTextField(10);
    private JLabel queueSelectionStrategyText = new JLabel("Queue Selection Strategy");

    private JButton start = new JButton("Start Simulation");
    private SimulationManager sim;
    private JComboBox<String> dropdownButton;




    public SimulationFrame(SimulationManager sim) {
        this.sim = sim;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 600);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.getContentPane().setBackground(Color.cyan);


        //Labels
        title.setBounds(200, 0, 400, 80);
        Font font = title.getFont();
        font = font.deriveFont(20.0f);
        title.setFont(font);

        timeLimitText.setBounds(20, 100, 200, 50);
        font = timeLimitText.getFont();
        font = font.deriveFont(20.0f);
        timeLimitText.setFont(font);

        numberOfClientsText.setBounds(20, 150, 200, 50);
        font = numberOfClientsText.getFont();
        font = font.deriveFont(20.0f);
        numberOfClientsText.setFont(font);

        numberOfQueuesText.setBounds(20, 200, 200, 50);
        font = numberOfQueuesText.getFont();
        font = font.deriveFont(20.0f);
        numberOfQueuesText.setFont(font);

        arrivalTimeText.setBounds(20, 250, 200, 50);
        font = arrivalTimeText.getFont();
        font = font.deriveFont(20.0f);
        arrivalTimeText.setFont(font);

        serviceTimeIntervalText.setBounds(20, 300, 250, 50);
        font = serviceTimeIntervalText.getFont();
        font = font.deriveFont(20.0f);
        serviceTimeIntervalText.setFont(font);

        queueSelectionStrategyText.setBounds(20, 350, 300, 50);
        font = queueSelectionStrategyText.getFont();
        font = font.deriveFont(20.0f);
        queueSelectionStrategyText.setFont(font);

        //TextFields

        timeLimit.setBounds(300, 100, 150, 30);
        numberOfClients.setBounds(300, 150, 150, 30);
        numberOfQueues.setBounds(300, 200, 150, 30);
        arrivalTime1.setBounds(300, 250, 150, 30);
        arrivalTime2.setBounds(500, 250, 150, 30);
        serviceTime1.setBounds(300, 300, 150, 30);
        sericeTime2.setBounds(500, 300, 150, 30);


        //Buttons
        start.setBounds(240, 500, 200, 50);

        String[] options = {"SHORTEST_QUEUE", "SHORTEST_TIME"};
        dropdownButton = new JComboBox<>(options);
        dropdownButton.setBounds(300, 360, 150, 30);

        frame.add(dropdownButton);


        frame.add(title);
        frame.add(timeLimitText);
        frame.add(numberOfClientsText);
        frame.add(numberOfQueues);
        frame.add(numberOfQueuesText);
        frame.add(arrivalTimeText);
        frame.add(serviceTimeIntervalText);
        frame.add(queueSelectionStrategyText);
        frame.add(start);
        frame.add(timeLimit);
        frame.add(numberOfClients);
        frame.add(arrivalTime1);
        frame.add(arrivalTime2);
        frame.add(serviceTime1);
        frame.add(sericeTime2);

        start.addActionListener(e -> {
            try {
                String s = timeLimit.getText();
                int time = Integer.parseInt(s);
                s = numberOfClients.getText();
                int clients = Integer.parseInt(s);
                s = numberOfQueues.getText();
                int queues = Integer.parseInt(s);
                s = arrivalTime1.getText();
                int arTime1 = Integer.parseInt(s);
                s = arrivalTime2.getText();
                int arTime2 = Integer.parseInt(s);
                s = serviceTime1.getText();
                int serTime1 = Integer.parseInt(s);
                s = sericeTime2.getText();
                int serTime2 = Integer.parseInt(s);
                SelectionPolicy policy = SelectionPolicy.SHORTEST_TIME;
                if(dropdownButton.getSelectedIndex()==0)
                    policy = SelectionPolicy.SHORTEST_QUEUE;


                if (e.getSource() == start) {
                    sim.initialize(time, clients, queues, arTime1, arTime2, serTime1, serTime2, policy, 1000);
                    Thread t = new Thread(sim);
                    t.start();

                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(new JFrame(), "Invalid input");
            }
        });

        frame.revalidate();
        frame.repaint();
    }
}
