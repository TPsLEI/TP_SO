package com.grupo_a.projeto;

import javax.swing.*;

public class Kernel {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            Login loginForm = new Login();
            loginForm.setVisible(true);
        });

    }
}
