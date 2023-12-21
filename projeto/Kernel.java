package projeto;

import javax.swing.*;
import java.util.concurrent.LinkedBlockingQueue;

public class Kernel {
    public static void main(String[] args) {
        LinkedBlockingQueue<String> dataQueue = new LinkedBlockingQueue<>();

        SwingUtilities.invokeLater(() -> {
            Login loginForm = new Login();
        });

        MEM mem = new MEM("dados.csv");
        CPU thread = new CPU(dataQueue, mem);
        thread.start();
    }
}
