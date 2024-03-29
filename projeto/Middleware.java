import javax.swing.*;
import com.formdev.flatlaf.FlatDarkLaf;
import java.awt.*;
import java.nio.file.*;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.io.IOException;

public class Middleware {
    public static LinkedBlockingQueue<String> dataQueue;

    public Middleware(LinkedBlockingQueue<String> dataQueue, Estacao estacao) {
        Middleware.dataQueue = dataQueue;
        init(estacao);
        initMessageListener();
    }

    private void init(Estacao estacao) {
        UIManager.put("defaultFont", new Font("Arial", Font.PLAIN, 13));
        UIManager.put("TitlePane.unifiedBackground", false);

        Color darkerBackground = new Color(30, 29, 30);

        UIManager.put("Panel.background", darkerBackground);

        FlatDarkLaf.setup();
        EventQueue.invokeLater(() -> new MessagePage(estacao).setVisible(true));
    }

    /*
     * Thread que fica à espera de uma alteração no ficheiro CSV interno das mensagens
     */
    private void initMessageListener() {
        Path filePath = Paths.get("files/dados.csv");
  
        Thread messageListenerThread = new Thread(() -> {
            try {

                /*
                * Cria um WatchService que fica à espera de uma alteração no ficheiro
                */
                WatchService watchService = FileSystems.getDefault().newWatchService();
                filePath.getParent().register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

                /*
                * Caso haja alguma alteração, o sistema avisa no terminal de uma Mensagem Recebida
                */
                while (true) {
                    WatchKey key = watchService.take();

                    for (WatchEvent<?> event : key.pollEvents()) {
                        if (event.context().toString().equals(filePath.getFileName().toString())) {
                            System.out.println("Mensagem recebida!");
                        }
                    }

                    key.reset();
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        messageListenerThread.setDaemon(true);
        messageListenerThread.start();
    }
    
    /*
     * Recebe as mensagens mandadas pelo utilizador e adiciona à DataQueue
     */
    public static void handleMessage(List<String> messages, MessagePage messagesPage) {
        dataQueue.addAll(messages);
    }

}
