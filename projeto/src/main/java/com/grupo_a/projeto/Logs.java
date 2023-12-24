package com.grupo_a.projeto;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.Semaphore;
import java.nio.file.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Semaphore;

public class Logs {
    private static final String LOG_FILE = "./projeto/src/main/java/com/grupo_a/projeto/files/logs.csv";
    private static final Semaphore semaphore = new Semaphore(1);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public static void log(String message) {
        try {
            semaphore.acquire();
            Files.createDirectories(Paths.get("./projeto/src/main/java/com/grupo_a/projeto/files"));
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
                LocalDateTime timestamp = LocalDateTime.now();
                String formattedTimestamp = timestamp.format(formatter);
                writer.write(formattedTimestamp + " , " + message + "\n");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }
}

