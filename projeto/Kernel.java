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

    /*
     * Função principal do Projeto, que corre a interface do Login
     */
    public static void main(String[] args) {

        UIManager.put("defaultFont", new Font("Arial", Font.PLAIN, 13));
        UIManager.put("TitlePane.unifiedBackground", false);

        Color darkerBackground = new Color(30, 29, 30);

        UIManager.put("Panel.background", darkerBackground);

        FlatDarkLaf.setup();
        EventQueue.invokeLater(() -> new Login().setVisible(true));

    }

    /*
     * Função de verificação das credenciais do Formulário
     */
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
                            JOptionPane.showMessageDialog(null, "Password incorreta, tente novamente!");
                        }
                        break;
                    }
                }
            }

            if (!userFound) {
                JOptionPane.showMessageDialog(null, "Utilizador não encontrado. Tente novamente!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * Função para exportar as Mensagens para um ficheiro CSV
     */
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

    /*
     * Função para exportar as Logs do Sistema para um ficheiro CSV
     */
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

    /*
     * Função para atualizar a TextBox do Satélite periodicamente
     */
    public static void updateTextBoxPeriodically(JTextArea textBox) {
        SwingWorker<Void, String> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                while (!isCancelled()) {
                    try (InputStream inputStream = Kernel.class.getClassLoader().getResourceAsStream("files/dados.csv")) {
                        if (inputStream != null) {
                            byte[] bytes = inputStream.readAllBytes();
                            String fileContent = new String(bytes, StandardCharsets.UTF_8);
                            textBox.setText(fileContent);
                        } else {
                            textBox.setText("Erro! Mensagens não encontradas");
                        }
                    } catch (IOException e) {
                        textBox.setText("Erro! Mensagens não carregadas");
                    }
    
                    TimeUnit.SECONDS.sleep(5);
                }
                return null;
            }
    
            @Override
            protected void process(java.util.List<String> chunks) {
                for (String chunk : chunks) {
                    textBox.append(chunk + "\n"); 
                }
            }
        };
    
        worker.execute();
    }
    

    /*
     * Função que responde à mensagem enviada pelo Utilizador
     */
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
