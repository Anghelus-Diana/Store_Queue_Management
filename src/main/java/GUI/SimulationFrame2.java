package GUI;

import BusinessLogic.Scheduler;
import BusinessLogic.SimulationManager;
import Model.Task;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SimulationFrame2 {
    JFrame frame = new JFrame();

    private JLabel currentTime = new JLabel("Current Time:");

    private JLabel waitingText = new JLabel("Waiting ");
    private JTextArea waiting =new JTextArea();

    private JLabel serversText = new JLabel("Queues:");
    private JTextArea servers =new JTextArea();


    public SimulationFrame2() {
        frame.setSize(800, 600);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.getContentPane().setBackground(Color.cyan);

        currentTime.setBounds(30, 50, 300, 50);
        Font font = currentTime.getFont();
        font = font.deriveFont(20.0f);
        currentTime.setFont(font);

        serversText.setBounds(190, 100, 300, 50);
        font = waitingText.getFont();
        font = font.deriveFont(20.0f);
        waitingText.setFont(font);

        waitingText.setBounds(30, 100, 300, 50);
        font = serversText.getFont();
        font = font.deriveFont(20.0f);
        serversText.setFont(font);

        servers.setBounds(180, 150, 500, 300);
        waiting.setBounds(30, 150, 70, 300);

        frame.add(serversText);
        frame.add(servers);
        frame.add(currentTime);
        frame.add(waiting);
        frame.add(waitingText);

    }

    public void printQueue(Scheduler scheduler, List<Task> tasks, int time){
        String s="";
        for (Task t: tasks){
            s+= t+ "\n";

        }
        waiting.setText(s);
        servers.setText(scheduler.print());
        currentTime.setText("Current time: "+time);
        // waiting.setText();
    }
    }
