package com.grupo_a.projeto;

import org.pushingpixels.substance.api.skin.SubstanceRavenLookAndFeel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.border.EmptyBorder;
import org.json.JSONObject;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Login extends BaseFrame implements ActionListener {
    private JButton loginButton;
    private JLabel userLabel, passLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public Login() {
        super("Login");

        System.setProperty("substance.laf.decorations", "true");
        System.setProperty("substance.laf.componentFocusKind", "NONE");
        System.setProperty("substance.laf.skin", "org.pushingpixels.substance.api.skin.SubstanceRavenLookAndFeel");
        System.setProperty("substance.laf.theme", "org.pushingpixels.substance.api.skin.SubstanceLightTheme");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));

        userLabel = new JLabel("Username");
        usernameField = new JTextField(15);
        passLabel = new JLabel("Password");
        passwordField = new JPasswordField(15);

        usernameField.addActionListener(this);

        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    loginButton.doClick();
                }
            }
        });

        panel.add(userLabel);
        panel.add(usernameField);
        panel.add(passLabel);
        panel.add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(30, 144, 255));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);

        add(panel, BorderLayout.CENTER);
        add(loginButton, BorderLayout.SOUTH);

        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        loginButton.addActionListener(this);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == loginButton) {
            String userValue = usernameField.getText();
            String passValue = passwordField.getText();
            boolean userFound = false;

            try {
                InputStream inputStream = Login.class.getClassLoader()
                        .getResourceAsStream("com/grupo_a/projeto/files/users.json");
                if (inputStream != null) {
                    String jsonContent = new String(inputStream.readAllBytes());
                    JSONObject json = new JSONObject(jsonContent);

                    for (String key : json.keySet()) {
                        JSONObject userData = json.getJSONObject(key);
                        String username = userData.getString("username");

                        if (username.equals(userValue)) {
                            userFound = true;
                            String storedPassword = userData.getString("password");

                            if (passValue.equals(storedPassword)) {
                                String name = userData.getString("name");

                                Logs.log("O Utilizador " + name + " autenticou-se.");

                                MenuPage page = new MenuPage(name);

                                page.addWindowListener(new java.awt.event.WindowAdapter() {
                                    @Override
                                    public void windowOpened(java.awt.event.WindowEvent windowEvent) {
                                        dispose();
                                    }
                                });

                                page.setVisible(true);
                            } else {
                                System.out.println("Password errada. Por favor, tente novamente.");
                            }
                            break;
                        }
                    }
                }

                if (!userFound) {
                    System.out.println("Utilizador não encontrado. Por favor, tente novamente.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
