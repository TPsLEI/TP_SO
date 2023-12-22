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

        // addLogo();
        setApplicationIcon();

        setLocationRelativeTo(null);
    }

    public BaseFrame(String title) {
        this();
        setTitle(title);
    }

    private void addLogo() {
        try {
            ImageIcon logoIcon = new ImageIcon(getClass().getResource("/com/grupo_a/projeto/logo/logo.png"));

            JLabel logoLabel = new JLabel(logoIcon);
            logoLabel.setHorizontalAlignment(JLabel.CENTER);

            add(logoLabel, BorderLayout.NORTH);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading logo: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setApplicationIcon() {
        try {
            BufferedImage logoImage = ImageIO.read(getClass().getResource("/com/grupo_a/projeto/logo/logo.png"));

            setIconImage(logoImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
