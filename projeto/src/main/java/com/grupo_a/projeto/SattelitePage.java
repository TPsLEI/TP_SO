package com.grupo_a.projeto;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class SattelitePage extends BaseFrame {

    private JTextArea textBox;

    public SattelitePage(String name) {
        super("Satélite");
        setSize(960, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        textBox = new JTextArea();
        textBox.setPreferredSize(new Dimension(650, 200));
        textBox.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 0));
        textBox.setBackground(Color.BLACK);
        textBox.setForeground(Color.WHITE);
        textBox.setEditable(false);

        updateTextBoxPeriodically();

        JButton deleteBox = new JButton("Limpar Consola");
        JButton exportMessages = new JButton("Exportar Mensagens");
        JButton seeGraph = new JButton("Ver gráfico");

        JButton seeLogs = new JButton("Ver Logs");
        JButton exportLogs = new JButton("Exportar Logs");
        JButton exitPage = new JButton("Voltar");

        int buttonHeight = 20;
        seeLogs.setPreferredSize(new Dimension(150, buttonHeight));
        exportLogs.setPreferredSize(new Dimension(150, buttonHeight));
        exitPage.setPreferredSize(new Dimension(150, buttonHeight));

        deleteBox.setPreferredSize(new Dimension(150, buttonHeight));
        exportMessages.setPreferredSize(new Dimension(150, buttonHeight));
        seeGraph.setPreferredSize(new Dimension(150, buttonHeight));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 25, 10, 0));
        buttonPanel.add(seeLogs);
        buttonPanel.add(exportLogs);
        buttonPanel.add(exitPage);

        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
        messagePanel.add(deleteBox);
        messagePanel.add(exportMessages);
        messagePanel.add(seeGraph);

        mainPanel.add(textBox, BorderLayout.WEST);
        mainPanel.add(messagePanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.PAGE_END);

        deleteBox.addActionListener(e -> {
            textBox.setText("");
            JOptionPane.showMessageDialog(SattelitePage.this, "Consola limpa com sucesso...");
            Logs.log("O Utilizador " + name + " limpou as mensagens.");
        });

        exportMessages.addActionListener(e -> {
            try (InputStream inputStream = SattelitePage.class.getClassLoader()
                    .getResourceAsStream("com/grupo_a/projeto/files/dados.csv")) {
                if (inputStream != null) {
                    byte[] bytes = inputStream.readAllBytes();
                    String fileContent = new String(bytes, StandardCharsets.UTF_8);
        
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Exportar Mensagens");
                    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        
                    int userSelection = fileChooser.showSaveDialog(SattelitePage.this);
                    if (userSelection == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = fileChooser.getSelectedFile();
        
                        String exportFilePath = selectedFile.getAbsolutePath();
                        if (!exportFilePath.toLowerCase().endsWith(".csv")) {
                            exportFilePath += ".csv";
                        }
        
                        try (BufferedWriter writer = new BufferedWriter(new FileWriter(exportFilePath))) {
                            writer.write(fileContent);
                            JOptionPane.showMessageDialog(SattelitePage.this, "Mensagens exportadas para " + exportFilePath);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(SattelitePage.this, "Erro ao exportar as mensagens");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(SattelitePage.this, "Erro! Mensagens não encontradas");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(SattelitePage.this, "Erro! Mensagens não carregadas");
            }
        });

        seeGraph.addActionListener(e -> {
            GraphPage graphPage = new GraphPage();
            graphPage.setVisible(true);
            Logs.log("O Utilizador " + name + " acedeu ao gráfico.");
        });


        seeLogs.addActionListener(e -> {
            LogsPage logsPage = new LogsPage();
            logsPage.setVisible(true);
            Logs.log("O Utilizador " + name + " acedeu às logs.");
        });

        exportLogs.addActionListener(e -> {
            try (InputStream inputStream = SattelitePage.class.getClassLoader()
                    .getResourceAsStream("com/grupo_a/projeto/files/logs.csv")) {
                if (inputStream != null) {
                    byte[] bytes = inputStream.readAllBytes();
                    String fileContent = new String(bytes, StandardCharsets.UTF_8);
        
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Exportar Logs");
                    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        
                    int userSelection = fileChooser.showSaveDialog(SattelitePage.this);
                    if (userSelection == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = fileChooser.getSelectedFile();
        
                        String exportFilePath = selectedFile.getAbsolutePath();
                        if (!exportFilePath.toLowerCase().endsWith(".csv")) {
                            exportFilePath += ".csv";
                        }
        
                        try (BufferedWriter writer = new BufferedWriter(new FileWriter(exportFilePath))) {
                            writer.write(fileContent);
                            JOptionPane.showMessageDialog(SattelitePage.this, "Logs exportadas para " + exportFilePath);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(SattelitePage.this, "Erro ao exportar as logs");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(SattelitePage.this, "Erro! Logs não encontradas");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(SattelitePage.this, "Erro! Logs não carregadas");
            }
        });

        exitPage.addActionListener(e -> {
            dispose();
            Logs.log("O Utilizador " + name + " desconectou-se do satélite.");
        });

        add(mainPanel);
    }

    private void updateTextBoxPeriodically() {
        SwingWorker<Void, String> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                while (!isCancelled()) {
                    try (InputStream inputStream = SattelitePage.class.getClassLoader()
                            .getResourceAsStream("com/grupo_a/projeto/files/dados.csv")) {
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
}

