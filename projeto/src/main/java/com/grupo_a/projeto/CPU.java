package com.grupo_a.projeto;

import java.time.LocalDateTime;
import java.util.concurrent.LinkedBlockingQueue;

public class CPU extends Thread {
    private LinkedBlockingQueue<String> dataQueue;
    private MEM mem;

    public CPU(LinkedBlockingQueue<String> dataQueue, MEM mem) {
        this.dataQueue = dataQueue;
        this.mem = mem;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String message = dataQueue.take();

                if ("stop".equals(message)) {
                    System.out.println("Programa encerrado.");
                    break;
                }

                LocalDateTime timestamp = LocalDateTime.now();
                String formattedMessage = timestamp + "," + message;

                mem.writeMessage(formattedMessage);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
