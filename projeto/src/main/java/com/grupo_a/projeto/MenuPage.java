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

        JButton accessSatelliteButton = new JButton("Access Satellite");
        JButton sendMessageButton = new JButton("Send Message");

        accessSatelliteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Logs.log("O Utilizador " + name + " acedeu ao satélite.");
                SattelitePage sattelitePage = new SattelitePage(name);
                sattelitePage.setVisible(true);
            }
        });

        sendMessageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Middleware middleware = new Middleware(dataQueue);
                MEM mem = new MEM("dados.csv");
                CPU thread = new CPU(dataQueue, mem, name);
                thread.start();
            }
        });

        setLayout(new BorderLayout());

        JLabel instructionLabel = new JLabel("Bem-vindo " + name);
        instructionLabel.setHorizontalAlignment(JLabel.LEFT);
        instructionLabel.setBorder(BorderFactory.createEmptyBorder(15, 15, 0, 0));
        instructionLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        add(instructionLabel, BorderLayout.NORTH);

        Box buttonBox = Box.createVerticalBox();
        buttonBox.add(Box.createVerticalGlue());

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(accessSatelliteButton);
        buttonPanel.add(sendMessageButton);

        buttonBox.add(buttonPanel);
        buttonBox.add(Box.createVerticalGlue());

        add(buttonBox, BorderLayout.CENTER);

        // Middleware middleware = new Middleware(dataQueue);

        // MEM mem = new MEM("dados.csv");
        // CPU thread = new CPU(dataQueue, mem);
        // thread.start();
    }

}
