package com.grupo_a.projeto;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class BaseFrame extends JFrame {

    public BaseFrame() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BorderLayout());
        
        //addLogo();
        setApplicationIcon();

        setLocationRelativeTo(null);
    }

    public BaseFrame(String title) {
        this(); 
        setTitle(title);
    }
    
    private void addLogo() {
        try {
            // Load the logo image using an absolute path
            ImageIcon logoIcon = new ImageIcon(getClass().getResource("/com/grupo_a/projeto/logo.png"));

            // Create a JLabel to display the logo
            JLabel logoLabel = new JLabel(logoIcon);
            logoLabel.setHorizontalAlignment(JLabel.CENTER);

            // Add the logo label to the frame
            add(logoLabel, BorderLayout.NORTH);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading logo: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void setApplicationIcon() {
        try {
            // Load the logo image
            BufferedImage logoImage = ImageIO.read(getClass().getResource("/com/grupo_a/projeto/logo.png"));

            // Set the application icon
            setIconImage(logoImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
