package com.grupo_a.projeto;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.Semaphore;
import java.nio.file.*;

public class Logs {
    private static final String LOG_FILE = "./projeto/src/main/java/com/grupo_a/projeto/files/logs.csv";
    private static final Semaphore semaphore = new Semaphore(1);

    public static void log(String message) {
        try {
            semaphore.acquire();
            Files.createDirectories(Paths.get("./projeto/src/main/java/com/grupo_a/projeto/files"));
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
                LocalDateTime timestamp = LocalDateTime.now();
                writer.write(timestamp + "," + message + "\n");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }
}
