import javax.swing.*;
import java.awt.*;
import java.nio.file.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class Middleware {

    private JTextField textField;
    private LinkedBlockingQueue<String> dataQueue;

    public Middleware(LinkedBlockingQueue<String> dataQueue) {
        this.dataQueue = dataQueue;
        initUI();
        initMessageListener();
    }

    private void initUI() {
        MessagePage frame = new MessagePage();
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel label = new JLabel("Introduzir a Mensagem:");
        panel.add(label, BorderLayout.NORTH);

        textField = new JTextField();
        panel.add(textField, BorderLayout.CENTER);

        JButton sendButton = new JButton("Enviar Mensagem");
        JButton voltarButton = new JButton("Voltar");

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(sendButton);
        buttonPanel.add(voltarButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        frame.add(panel);

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendButton.doClick();
                }
            }
        });

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = textField.getText();
                dataQueue.offer(message);
                textField.setText("");
            }
        });

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        frame.setVisible(true);
    }

    private void initMessageListener() {
        Path filePath = Paths.get("files/dados.csv");

        Thread messageListenerThread = new Thread(() -> {
            try {
                WatchService watchService = FileSystems.getDefault().newWatchService();
                filePath.getParent().register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

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
    

}
