import javax.swing.*;
import java.awt.*;
import com.formdev.flatlaf.*;
import net.miginfocom.swing.MigLayout;

public class SattelitePage extends JFrame {
    private JTextArea textBox;
    private JButton exportMessages;
    private JButton seeGraph;
    private JButton seeLogs;
    private JButton exportLogs;
    private JButton exitPage;

    public SattelitePage(Estacao estacao) {
        setTitle("Satélite");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(1200, 700));
        setLocationRelativeTo(null);
        Kernel.setApplicationIcon(this);
        setResizable(false);
        initSattelite(estacao);
    }

    private void initSattelite(Estacao estacao) {
        setLayout(new MigLayout("fill,insets 20", "[center]", "[center]"));

        textBox = new JTextArea();
        exportMessages = new JButton("Exportar Mensagens");
        seeGraph = new JButton("Ver gráfico");
        seeLogs = new JButton("Ver Logs");
        exportLogs = new JButton("Exportar Logs");
        exitPage = new JButton("Voltar");


        JPanel panel = new JPanel(
                new MigLayout("wrap 3, fillx, insets 35 45 30 45", "[fill,250:280][fill,250:280][fill,250:280]"));

        Color darkerBackground = new Color(38, 37, 38);
        String darkerBackgroundStyle = String.format("rgb(%d,%d,%d)", darkerBackground.getRed(),
                darkerBackground.getGreen(), darkerBackground.getBlue());

        Color black = new Color(15, 15, 15);

        panel.putClientProperty(FlatClientProperties.STYLE, "" +
                "arc:20;" +
                "[light]background:darken(@background,3%);" +
                "[dark]background:" + darkerBackgroundStyle + ";");

        textBox.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 0));
        textBox.setBackground(black);
        textBox.setEditable(false);

        Kernel.updateTextBoxPeriodically(textBox);

        Color lighterBackground = new Color(55, 53, 55);
        String lighterBackgroundStyle = String.format("rgb(%d,%d,%d)", lighterBackground.getRed(),
                lighterBackground.getGreen(), lighterBackground.getBlue());

        exportMessages.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]background:darken(@background,10%);" +
                "[dark]background:" + lighterBackgroundStyle + ";" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0");

        seeGraph.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]background:darken(@background,10%);" +
                "[dark]background:" + lighterBackgroundStyle + ";" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0");

        seeLogs.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]background:darken(@background,10%);" +
                "[dark]background:" + lighterBackgroundStyle + ";" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0");

        exportLogs.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]background:darken(@background,10%);" +
                "[dark]background:" + lighterBackgroundStyle + ";" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0");

        exitPage.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]background:darken(@background,10%);" +
                "[dark]background:" + lighterBackgroundStyle + ";" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0");

        exportMessages.addActionListener(e -> {
            Kernel.exportMessages("files/dados.csv", estacao.name);
        });

        seeGraph.addActionListener(e -> {
            GraphPage graphPage = new GraphPage();
            graphPage.setVisible(true);
            MEM.log("O Utilizador " + estacao.name + " acedeu ao gráfico.");
        });

        seeLogs.addActionListener(e -> {
            LogsPage logsPage = new LogsPage();
            logsPage.setVisible(true);
            MEM.log("O Utilizador " + estacao.name + " acedeu às logs.");
        });

        exportLogs.addActionListener(e -> {
            Kernel.exportLogs("files/logs.csv", estacao.name);
        });

        exitPage.addActionListener(e -> {
            MEM.log("O Utilizador " + estacao.name + " desconectou-se do satélite.");
            UIManager.put("defaultFont", new Font("Arial", Font.PLAIN, 13));
            UIManager.put("TitlePane.unifiedBackground", false);

            Color Background = new Color(30, 29, 30);

            UIManager.put("Panel.background", Background);

            FlatDarkLaf.setup();
            MenuPage page = new MenuPage(estacao);

            page.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowOpened(java.awt.event.WindowEvent windowEvent) {
                    SattelitePage.this.dispose();
                }
            });

            page.setVisible(true);
        });

        JScrollPane scrollPane = new JScrollPane(textBox, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panel.add(scrollPane, "span, grow, wrap");
        panel.add(seeGraph, "grow, push");
        panel.add(exportMessages, "grow, push");
        panel.add(exitPage, "grow, push");
        panel.add(seeLogs, "grow, push");
        panel.add(exportLogs, "grow, push");

        add(panel);
    }
}
