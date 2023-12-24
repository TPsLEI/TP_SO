package com.grupo_a.projeto;

import java.io.IOException;
import java.io.InputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.charset.StandardCharsets;

public class SattelitePage extends BaseFrame {

    public SattelitePage() {
        super("Satélite");
        setSize(960, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JTextArea textBox = new JTextArea();
        textBox.setPreferredSize(new Dimension(650, 200));
        textBox.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 0));
        textBox.setBackground(Color.BLACK);
        textBox.setForeground(Color.WHITE);
        textBox.setEditable(false);

        try (InputStream inputStream = SattelitePage.class.getClassLoader()
                .getResourceAsStream("com/grupo_a/projeto/files/dados.csv");) {
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

        JButton deleteBox = new JButton("Limpar Consola");
        JButton exportMessages = new JButton("Exportar Mensagens");

        JButton seeLogs = new JButton("Ver Logs");
        JButton exportLogs = new JButton("Exportar Logs");
        JButton exitPage = new JButton("Voltar");

        int buttonHeight = 20;
        seeLogs.setPreferredSize(new Dimension(150, buttonHeight));
        exportLogs.setPreferredSize(new Dimension(150, buttonHeight));
        exitPage.setPreferredSize(new Dimension(150, buttonHeight));

        deleteBox.setPreferredSize(new Dimension(150, buttonHeight));
        exportMessages.setPreferredSize(new Dimension(150, buttonHeight));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 25, 10, 0));
        buttonPanel.add(seeLogs);
        buttonPanel.add(exportLogs);
        buttonPanel.add(exitPage);

        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS)); // Use BoxLayout with Y_AXIS
        messagePanel.add(deleteBox);
        messagePanel.add(exportMessages);

        mainPanel.add(textBox, BorderLayout.WEST);
        mainPanel.add(messagePanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.PAGE_END);

        deleteBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textBox.setText("");
                JOptionPane.showMessageDialog(SattelitePage.this, "Consola limpa com sucesso..");
            }
        });

        exportMessages.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("clicou no botao de exportar mensagens");
            }
        });

        seeLogs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LogsPage logsPage = new LogsPage();
                logsPage.setVisible(true);
            }
        });

        exportLogs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("clicou no botao de exportar as logs");
            }
        });

        exitPage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        add(mainPanel);
    }
}
