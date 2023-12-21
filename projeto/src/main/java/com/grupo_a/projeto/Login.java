package com.grupo_a.projeto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.*;
import org.json.JSONObject;
import org.json.JSONArray;

public class Login extends JFrame implements ActionListener {
    JButton b1;
    JFrame frame;
    JLabel userLabel, passLabel;
    final JTextField textField1, textField2;

    Login() {
        frame = new JFrame();
        frame.setTitle("Login");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        userLabel = new JLabel("Username");
        textField1 = new JTextField(15);
        passLabel = new JLabel("Password");
        textField2 = new JPasswordField(15);

        panel.add(userLabel);
        panel.add(textField1);
        panel.add(passLabel);
        panel.add(textField2);

        b1 = new JButton("Iniciar Sessao");

        frame.add(panel, BorderLayout.CENTER);
        frame.add(b1, BorderLayout.SOUTH);

        b1.addActionListener(this);

        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        String userValue = textField1.getText();
        String passValue = textField2.getText();

        try {
            String jsonFilePath = "src/main/java/com/grupo_a/projeto/users.json";
            String jsonContent = new String(Files.readAllBytes(Paths.get(jsonFilePath)));
            JSONObject json = new JSONObject(jsonContent);

            boolean userFound = false;
            for (String key : json.keySet()) {
                JSONObject userData = json.getJSONObject(key);
                String username = userData.getString("username");

                if (username.equals(userValue)) {
                    userFound = true;
                    String storedPassword = userData.getString("password");

                    if (passValue.equals(storedPassword)) {
                        String name = userData.getString("name");

                        MenuPage page = new MenuPage();
                        JLabel wel_label = new JLabel("Bem-Vindo: " + name);
                        page.getContentPane().add(wel_label);

                        page.addWindowListener(new java.awt.event.WindowAdapter() {
                            @Override
                            public void windowOpened(java.awt.event.WindowEvent windowEvent) {
                                frame.dispose();
                            }
                        });

                        page.setVisible(true);
                    } else {
                        System.out.println("Password errada. Por favor, tente novamente.");
                    }
                    break;
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
