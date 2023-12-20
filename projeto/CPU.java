package projeto;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;

public class CPU extends Thread {
    private LinkedBlockingQueue<String> dataQueue;
    private String csvFileName;

    public CPU(LinkedBlockingQueue<String> dataQueue, String csvFileName) {
        this.dataQueue = dataQueue;
        this.csvFileName = csvFileName;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Insira uma mensagem:");
                String message = scanner.nextLine();
                if ("stop".equals(message)) {
                    System.out.println("Programa encerrado.");
                    break;
                }

                LocalDateTime timestamp = LocalDateTime.now();

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFileName, true))) {
                    writer.write(timestamp + "," + message + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
