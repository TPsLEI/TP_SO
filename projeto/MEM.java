import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Semaphore;

public class MEM {
    private String csvFileName;
    private static final String LOG_FILE = "files/logs.csv";
    private static final Semaphore semaphore = new Semaphore(1);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public MEM(String csvFileName) {
        String currentPath = "files";
        this.csvFileName = Paths.get(currentPath, csvFileName).toString();
    }

    public synchronized void writeMessage(String message, String name) {
        CompletableFuture.runAsync(() -> {
            MEM.log("O Utilizador " + name + " enviou uma mensagem.");
            try (BufferedWriter writer = new BufferedWriter(
                    new FileWriter(csvFileName, StandardCharsets.UTF_8, true))) {
                        writer.write(message + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static synchronized void log(String message) {
        CompletableFuture.runAsync(() -> {
            try {
                long threadId = Thread.currentThread().getId();
                semaphore.acquire();
                Files.createDirectories(Paths.get("files"));
                try (BufferedWriter writer = new BufferedWriter(
                        new FileWriter(LOG_FILE, StandardCharsets.UTF_8, true))) {
                    LocalDateTime timestamp = LocalDateTime.now();
                    String formattedTimestamp = timestamp.format(formatter);
                    writer.write(formattedTimestamp + ", Thread " + threadId + ", " + message + "\n");
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
            }
        });
    }
}
