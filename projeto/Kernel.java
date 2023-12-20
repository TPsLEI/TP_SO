package projeto;

import java.util.concurrent.LinkedBlockingQueue;

public class Kernel {
    public static void main(String[] args) {
        LinkedBlockingQueue<String> dataQueue = new LinkedBlockingQueue<>();

        CPU thread = new CPU(dataQueue, "dados.csv");
        thread.start();
    }
}