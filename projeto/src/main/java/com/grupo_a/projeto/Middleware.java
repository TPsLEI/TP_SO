package com.grupo_a.projeto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.LinkedBlockingQueue;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Middleware {

    private JTextField textField;
    private LinkedBlockingQueue<String> dataQueue;

    public Middleware(LinkedBlockingQueue<String> dataQueue) {
        this.dataQueue = dataQueue;
        initUI();
    }

    private void initUI() {
        MessagePage frame = new MessagePage();
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create and configure a JPanel for a cleaner layout
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel label = new JLabel("Introduzir a Mensagem:");
        panel.add(label, BorderLayout.NORTH);

        textField = new JTextField();
        panel.add(textField, BorderLayout.CENTER);

        JButton sendButton = new JButton("Enviar Mensagem");
        JButton voltarButton = new JButton("Voltar");

        // Create a subpanel for the buttons and use FlowLayout
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(sendButton);
        buttonPanel.add(voltarButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Add an EmptyBorder to give some padding around the panel
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add the panel to the frame
        frame.add(panel);

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendButton.doClick();
                }
            }
        });

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = textField.getText();
                dataQueue.offer(message);
                textField.setText("");
            }
        });

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the MessagePage window
            }
        });

        frame.setVisible(true);
    }



}
