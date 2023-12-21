package projeto;

import javax.swing.*;
import java.util.concurrent.LinkedBlockingQueue;

public class Kernel {
    public static void main(String[] args) {
        LinkedBlockingQueue<String> dataQueue = new LinkedBlockingQueue<>();

        SwingUtilities.invokeLater(() -> {
            Middleware middleware = new Middleware(dataQueue);
        });

        MEM mem = new MEM("dados.csv");
        CPU thread = new CPU(dataQueue, mem);
        thread.start();
    }
}
