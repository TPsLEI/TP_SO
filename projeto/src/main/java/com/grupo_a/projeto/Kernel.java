package com.grupo_a.projeto;

import javax.swing.*;
import java.util.concurrent.LinkedBlockingQueue;

public class Kernel {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            Login loginForm = new Login();
        });

    }
}
