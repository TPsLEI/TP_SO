package com.grupo_a.projeto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.LinkedBlockingQueue;

public class MenuPage extends BaseFrame {
    LinkedBlockingQueue<String> dataQueue = new LinkedBlockingQueue<>();

    public MenuPage(String name) {
        super("Menu");
        setSize(450, 170);

        // Create buttons for different options
        JButton accessSatelliteButton = new JButton("Access Satellite");
        JButton sendMessageButton = new JButton("Send Message");

        // Set up action listeners for the buttons
        accessSatelliteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(MenuPage.this, "Accessing Satellite...");
            }
        });

        sendMessageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Middleware middleware = new Middleware(dataQueue);
                MEM mem = new MEM("dados.csv");
                CPU thread = new CPU(dataQueue, mem);
                thread.start();
            }
        });

        // Set layout manager for the frame
        setLayout(new BorderLayout());

        // Add a label with instructions
        JLabel instructionLabel = new JLabel("Bem-vindo " + name);
        instructionLabel.setHorizontalAlignment(JLabel.LEFT);
        instructionLabel.setBorder(BorderFactory.createEmptyBorder(15, 15, 0, 0)); // Add padding
        instructionLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        add(instructionLabel, BorderLayout.NORTH);

        // Create a Box to center the buttons vertically
        Box buttonBox = Box.createVerticalBox();
        buttonBox.add(Box.createVerticalGlue()); // Top glue

        // Add buttons to the frame in a horizontal layout
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(accessSatelliteButton);
        buttonPanel.add(sendMessageButton);

        buttonBox.add(buttonPanel);
        buttonBox.add(Box.createVerticalGlue()); // Bottom glue

        add(buttonBox, BorderLayout.CENTER);
    
        //Middleware middleware = new Middleware(dataQueue);

        //MEM mem = new MEM("dados.csv");
        //CPU thread = new CPU(dataQueue, mem);
        //thread.start();
    }

}
