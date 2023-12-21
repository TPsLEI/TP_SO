package projeto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        userLabel = new JLabel("Email");
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

        if (userValue.equals("josedmagalhaes100809@gmail.com") && passValue.equals("08092002")) {
            MenuPage page = new MenuPage();
            JLabel wel_label = new JLabel("Bem-Vindo: " + userValue);
            page.getContentPane().add(wel_label);

            page.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowOpened(java.awt.event.WindowEvent windowEvent) {
                    frame.dispose();
                }
            });

            page.setVisible(true);
        } else {
            System.out.println("Por favor insira um Email e uma Password válidos");
        }
    }
}