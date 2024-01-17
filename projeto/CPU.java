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
                /*
                 * Guarda numa variável a primeira mensagem da DataQueue
                 */
                String message = dataQueue.take();

                /*
                 * Guarda as informações sobre o Data e Hora
                 */
                LocalDateTime timestamp = LocalDateTime.now();

                /*
                 * Formata a Data e Hora e a Mensagem guardada no ficheiro CSV
                 */
                String formattedTimestamp = timestamp.format(formatter);
                String formattedMessage = formattedTimestamp + " , De: " + userName + " , \"" + message + "\"";

                mem.writeMessage(formattedMessage, userName);

                /*
                 * A Thread descansa durante 3 segundos antes de ficar a espera de outra mensagem na DataQueue
                 */
                Thread.sleep(3000);

                showMessageBox(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * Envia uma resposta do Satélite, numa MessageBox
     */
    private void showMessageBox(String message) {
        Kernel.answerMessage(message, userName);
    }
}