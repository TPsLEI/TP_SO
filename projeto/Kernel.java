import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import org.json.JSONObject;
import javax.swing.*;
import java.awt.*;
import com.formdev.flatlaf.*;

public class Kernel {

    Thread userThread = new Thread();

    public static void main(String[] args) {

        UIManager.put("defaultFont", new Font("Arial", Font.PLAIN, 13));
        UIManager.put("TitlePane.unifiedBackground", false);

        Color darkerBackground = new Color(30, 29, 30);

        UIManager.put("Panel.background", darkerBackground);

        FlatDarkLaf.setup();
        EventQueue.invokeLater(() -> new Login().setVisible(true));

    }

    public static void handleLogin(String username, String password, Login loginPage) {
        boolean userFound = false;

        try {
            InputStream inputStream = Kernel.class.getClassLoader()
                    .getResourceAsStream("files/users.json");
            if (inputStream != null) {
                String jsonContent = new String(inputStream.readAllBytes());
                JSONObject json = new JSONObject(jsonContent);

                for (String key : json.keySet()) {
                    JSONObject userData = json.getJSONObject(key);
                    String storedUsername = userData.getString("username");

                    if (storedUsername.equals(username)) {
                        userFound = true;
                        String storedPassword = userData.getString("password");

                        if (password.equals(storedPassword)) {
                            Estacao estacao = new Estacao();
                            estacao.name = userData.getString("name");
                            estacao.username = userData.getString("username");
                            estacao.password = userData.getString("password");

                            MEM.log("O Utilizador " + estacao.name + " autenticou-se.");
                            UIManager.put("defaultFont", new Font("Arial", Font.PLAIN, 13));
                            UIManager.put("TitlePane.unifiedBackground", false);

                            Color darkerBackground = new Color(30, 29, 30);

                            UIManager.put("Panel.background", darkerBackground);

                            FlatDarkLaf.setup();
                            MenuPage page = new MenuPage(estacao);

                            page.addWindowListener(new java.awt.event.WindowAdapter() {
                                @Override
                                public void windowOpened(java.awt.event.WindowEvent windowEvent) {
                                    loginPage.dispose();
                                }
                            });

                            page.setVisible(true);
                        } else {
                            System.out.println("Password errada. Por favor, tente novamente.");
                        }
                        break;
                    }
                }
            }

            if (!userFound) {
                System.out.println("Utilizador não encontrado. Por favor, tente novamente.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void exportMessages(String filePath, String name) {
        try (InputStream inputStream = Kernel.class.getClassLoader().getResourceAsStream(filePath)) {
            if (inputStream != null) {
                byte[] bytes = inputStream.readAllBytes();
                String fileContent = new String(bytes, StandardCharsets.UTF_8);

                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Exportar Mensagens");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                int userSelection = fileChooser.showSaveDialog(null);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();

                    String exportFilePath = selectedFile.getAbsolutePath();
                    if (!exportFilePath.toLowerCase().endsWith(".csv")) {
                        exportFilePath += ".csv";
                    }

                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(exportFilePath))) {
                        writer.write(fileContent);
                        JOptionPane.showMessageDialog(null, "Mensagens exportadas para " + exportFilePath);
                        MEM.log("O Utilizador " + name + " exportou as mensagens.");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Erro ao exportar as mensagens");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Erro! Mensagens não encontradas");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro! Mensagens não carregadas");
        }
    }

    public static void exportLogs(String filePath, String name) {
        try (InputStream inputStream = Kernel.class.getClassLoader().getResourceAsStream(filePath)) {
            if (inputStream != null) {
                byte[] bytes = inputStream.readAllBytes();
                String fileContent = new String(bytes, StandardCharsets.UTF_8);

                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Exportar Logs");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                int userSelection = fileChooser.showSaveDialog(null);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();

                    String exportFilePath = selectedFile.getAbsolutePath();
                    if (!exportFilePath.toLowerCase().endsWith(".csv")) {
                        exportFilePath += ".csv";
                    }

                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(exportFilePath))) {
                        writer.write(fileContent);
                        JOptionPane.showMessageDialog(null, "Logs exportadas para " + exportFilePath);
                        MEM.log("O Utilizador " + name + " exportou as mensagens.");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Erro ao exportar as logs");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Erro! Logs não encontradas");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro! Logs não carregadas");
        }
    }

    public static void updateTextBoxPeriodically(JTextArea textBox) {
        SwingWorker<Void, String> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                while (!isCancelled()) {
                    try (InputStream inputStream = Kernel.class.getClassLoader()
                            .getResourceAsStream("files/dados.csv")) {
                        if (inputStream != null) {
                            byte[] bytes = inputStream.readAllBytes();
                            String fileContent = new String(bytes, StandardCharsets.UTF_8);
                            publish(fileContent);
                        } else {
                            publish("Erro! Mensagens não encontradas");
                        }
                    } catch (IOException e) {
                        publish("Erro! Mensagens não carregadas");
                    }

                    TimeUnit.SECONDS.sleep(5);
                }
                return null;
            }

            @Override
            protected void process(java.util.List<String> chunks) {
                for (String chunk : chunks) {
                    textBox.setText(chunk);
                }
            }
        };

        worker.execute();
    }

    public static void answerMessage(String message, String name) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(
                    null,
                    "Resposta a: " + message + "\nOk, " + name,
                    "Resposta do Satélite",
                    JOptionPane.INFORMATION_MESSAGE);
        });
    }

    public static void setApplicationIcon(JFrame frame) {
        try {
            BufferedImage logoImage = ImageIO.read(Kernel.class.getResource("logo/logo.png"));
            frame.setIconImage(logoImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
