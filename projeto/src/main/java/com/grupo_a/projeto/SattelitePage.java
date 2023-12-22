package com.grupo_a.projeto;

import javax.swing.*;
import java.awt.*;

public class SattelitePage extends BaseFrame {

    public SattelitePage() {
        super("Satélite");
        setSize(960, 600);

        // Create a panel for the main content
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Create a black box with white text
        JTextArea textBox = new JTextArea();
        textBox.setBackground(Color.BLACK);
        textBox.setForeground(Color.WHITE);
        textBox.setEditable(false); // Make it read-only
        textBox.setText("This is a black box with white text.");

        // Set max size and padding for the black box
        Dimension maxBoxSize = new Dimension(150, 100);
        textBox.setMaximumSize(maxBoxSize);
        textBox.setBorder(BorderFactory.createEmptyBorder(15, 5, 0, 0)); // Top and left padding

        // Create buttons under the box
        JButton button3 = new JButton("Button 3");
        JButton button4 = new JButton("Button 4");

        // Set height of buttons 3 and 4
        button3.setPreferredSize(new Dimension(100, 20));
        button4.setPreferredSize(new Dimension(100, 20));

        // Create buttons on the right side of the box
        JButton button1 = new JButton("Button 1");
        JButton button2 = new JButton("Button 2");

        // Set height of buttons 1 and 2
        button1.setPreferredSize(new Dimension(50, 20));
        button2.setPreferredSize(new Dimension(50, 20));

        // Add padding for button 1
        JPanel button1Panel = new JPanel();
        button1Panel.setLayout(new FlowLayout());
        button1Panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 25));
        button1Panel.add(button1);

        // Add padding and layout adjustments for buttons 3 and 4
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 25, 10, 0));
        buttonPanel.add(button3);
        buttonPanel.add(button4);

        // Set up the layout
        mainPanel.add(textBox, BorderLayout.CENTER);
        mainPanel.add(button1Panel, BorderLayout.EAST);
        mainPanel.add(button2, BorderLayout.EAST);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add the main panel to the frame
        add(mainPanel);
    }
}
