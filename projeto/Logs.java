import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.concurrent.Semaphore;
import java.time.format.DateTimeFormatter;

public class Logs {
    private static final String LOG_FILE = "files/logs.csv";
    private static final Semaphore semaphore = new Semaphore(1);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public static synchronized void log(String message) {
        try {
            semaphore.acquire();
            Files.createDirectories(Paths.get("files"));
            try (BufferedWriter writer = new BufferedWriter(
                    new FileWriter(LOG_FILE, StandardCharsets.UTF_8, true))) {
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
