import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class LogsPage extends BaseFrame {

    public LogsPage() {
        super("Logs");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JTextArea textBox = new JTextArea();
        textBox.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 0));
        textBox.setBackground(Color.BLACK);
        textBox.setForeground(Color.WHITE);
        textBox.setEditable(false);

        try (InputStream inputStream = SattelitePage.class.getClassLoader()
                .getResourceAsStream("files/logs.csv");) {
            if (inputStream != null) {
                byte[] bytes = inputStream.readAllBytes();
                String fileContent = new String(bytes, StandardCharsets.UTF_8);
                textBox.setText(fileContent);
            } else {
                System.out.println(inputStream);
                textBox.setText("Erro! Mensagens não encontradas");
            }
        } catch (IOException e) {
            e.printStackTrace();
            textBox.setText("Erro! Mensagens não carregadas");
        }
        JScrollPane scrollPane = new JScrollPane(textBox, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);
    }
}
