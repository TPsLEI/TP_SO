import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;
import javax.swing.*;

public class Kernel {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            Login loginForm = new Login();
            loginForm.setVisible(true);
        });

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
}
