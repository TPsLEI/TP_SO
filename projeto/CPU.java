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

    /*
     * Thread que fica sempre a correr à espera de uma mensagem na Queue
     */
    @Override
    public void run() {
        while (true) {
            try {
                String message = dataQueue.take();

                LocalDateTime timestamp = LocalDateTime.now();
                String formattedTimestamp = timestamp.format(formatter);
                String formattedMessage = formattedTimestamp + " , De: " + userName + " , \"" + message + "\"";

                mem.writeMessage(formattedMessage, userName);

                Thread.sleep(3000);

                showMessageBox(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void showMessageBox(String message) {
        Kernel.answerMessage(message, userName);
    }
}