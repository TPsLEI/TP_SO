package com.grupo_a.projeto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.LinkedBlockingQueue;

public class CPU extends Thread {
    private LinkedBlockingQueue<String> dataQueue;
    private MEM mem;
    private String userName;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public CPU(LinkedBlockingQueue<String> dataQueue, MEM mem, String userName) {
        this.dataQueue = dataQueue;
        this.mem = mem;
        this.userName = userName;
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
                String formattedTimestamp = timestamp.format(formatter);
                String formattedMessage = formattedTimestamp + " , De: " + userName + " , \"" + message + "\"";

                mem.writeMessage(formattedMessage, userName);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


