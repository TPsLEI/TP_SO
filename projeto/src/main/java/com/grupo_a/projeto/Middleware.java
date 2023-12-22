package com.grupo_a.projeto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.LinkedBlockingQueue;

public class Middleware {

    private JTextField textField;
    private LinkedBlockingQueue<String> dataQueue;

    public Middleware(LinkedBlockingQueue<String> dataQueue) {
        this.dataQueue = dataQueue;
        initUI();
    }

    private void initUI() {
        MessagePage frame = new MessagePage();
        frame.setSize(300, 200);

        JLabel label = new JLabel("Enter a message:");
        frame.add(label, BorderLayout.NORTH);

        textField = new JTextField();
        frame.add(textField, BorderLayout.CENTER);

        JButton sendButton = new JButton("Send Message");
        frame.add(sendButton, BorderLayout.SOUTH);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = textField.getText();
                dataQueue.offer(message);
                textField.setText("");
            }
        });

        frame.setVisible(true);
    }
}
