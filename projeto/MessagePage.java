import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import javax.swing.*;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import com.formdev.flatlaf.*;

import net.miginfocom.swing.MigLayout;

public class MessagePage extends JFrame implements ActionListener {
    LinkedBlockingQueue<String> dataQueue = new LinkedBlockingQueue<>();
    private JButton sendButton;
    private JButton send10MessagesButton;
    private JButton voltarButton;
    private JTextField textField;

    public MessagePage(Estacao estacao) {
        setTitle("Message");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(1200, 700));
        setLocationRelativeTo(null);
        Kernel.setApplicationIcon(this);
        setResizable(false);
        initMessage(estacao);
    }

    private void initMessage(Estacao estacao) {
        setLayout(new MigLayout("fill,insets 20", "[center]", "[center]"));

        sendButton = new JButton("Enviar Mensagem");
        send10MessagesButton = new JButton("Enviar 10 mensagens");
        voltarButton = new JButton("Voltar");
        textField = new JTextField();

        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 35 45 30 45", "fill,250:280"));

        Color darkerBackground = new Color(38, 37, 38);
        String darkerBackgroundStyle = String.format("rgb(%d,%d,%d)", darkerBackground.getRed(),
                darkerBackground.getGreen(), darkerBackground.getBlue());

        panel.putClientProperty(FlatClientProperties.STYLE, "" +
                "arc:20;" +
                "[light]background:darken(@background,3%);" +
                "[dark]background:" + darkerBackgroundStyle + ";");

        textField.putClientProperty(FlatClientProperties.STYLE, "" +
                "arc:5;" +
                "[light]background:darken(@background,3%);" +
                "[dark]background:" + darkerBackgroundStyle + ";");

        Color lighterBackground = new Color(55, 53, 55);
        String lighterBackgroundStyle = String.format("rgb(%d,%d,%d)", lighterBackground.getRed(),
                lighterBackground.getGreen(), lighterBackground.getBlue());

        sendButton.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]background:darken(@background,10%);" +
                "[dark]background:" + lighterBackgroundStyle + ";" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0");

        send10MessagesButton.putClientProperty(FlatClientProperties.STYLE, "" +
        "[light]background:darken(@background,10%);" +
        "[dark]background:" + lighterBackgroundStyle + ";" +
        "borderWidth:0;" +
        "focusWidth:0;" +
        "innerFocusWidth:0");

        voltarButton.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]background:darken(@background,10%);" +
                "[dark]background:" + lighterBackgroundStyle + ";" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0");

        send10MessagesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> messages = new ArrayList<>();
                for (int i = 1; i < 11; i++) {
                    messages.add("Teste Automático " + i);
                }
                Middleware.handleMessage(messages, MessagePage.this);
            }
        });

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UIManager.put("defaultFont", new Font("Arial", Font.PLAIN, 13));
                UIManager.put("TitlePane.unifiedBackground", false);

                Color darkerBackground = new Color(30, 29, 30);

                UIManager.put("Panel.background", darkerBackground);

                FlatDarkLaf.setup();
                MenuPage page = new MenuPage(estacao);

                page.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowOpened(java.awt.event.WindowEvent windowEvent) {
                        MessagePage.this.dispose();
                    }
                });

                page.setVisible(true);
            }
        });

        textField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "");

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendButton.doClick();
                }
            }
        });

        panel.add(new JLabel("Introduzir uma mensagem:"));
        panel.add(textField);
        panel.add(sendButton, "gapy 8");
        panel.add(send10MessagesButton, "gapy 2");
        panel.add(voltarButton, "gapy 3");

        sendButton.addActionListener(this);

        add(panel);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sendButton) {
            String message = textField.getText();
            List<String> messages = new ArrayList<>();
            messages.add(message);
            Middleware.handleMessage(messages, this);
            textField.setText("");
        }
    }
}
