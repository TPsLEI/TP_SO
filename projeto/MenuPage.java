import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.LinkedBlockingQueue;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.border.EmptyBorder;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import com.formdev.flatlaf.*;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

public class MenuPage extends JFrame {
    LinkedBlockingQueue<String> dataQueue = new LinkedBlockingQueue<>();

    private JButton accessSatelliteButton;
    private JButton sendMessageButton;

    public MenuPage(Estacao estacao) {
        setTitle("Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(1200, 700));
        setLocationRelativeTo(null);
        Kernel.setApplicationIcon(this);
        setResizable(false);
        initMenu(estacao);
    }

    private void initMenu(Estacao estacao) {
        setLayout(new MigLayout("fill,insets 20", "[center]", "[center]"));

        accessSatelliteButton = new JButton("Aceder ao Satélite");
        sendMessageButton = new JButton("Enviar Mensagem");

        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 35 45 30 45", "fill,250:280"));
        
        Color darkerBackground = new Color(38, 37, 38);
        String darkerBackgroundStyle = String.format("rgb(%d,%d,%d)", darkerBackground.getRed(),
                darkerBackground.getGreen(), darkerBackground.getBlue());

        panel.putClientProperty(FlatClientProperties.STYLE, "" +
                "arc:20;" +
                "[light]background:darken(@background,3%);" +
                "[dark]background:" + darkerBackgroundStyle + ";");

        Color lighterBackground = new Color(55, 53, 55);
        String lighterBackgroundStyle = String.format("rgb(%d,%d,%d)", lighterBackground.getRed(),
                lighterBackground.getGreen(), lighterBackground.getBlue());

        accessSatelliteButton.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]background:darken(@background,10%);" +
                "[dark]background:" + lighterBackgroundStyle + ";" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0");

        sendMessageButton.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]background:darken(@background,10%);" +
                "[dark]background:" + lighterBackgroundStyle + ";" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0");

        accessSatelliteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MEM.log("O Utilizador " + estacao.name + " acedeu ao satélite.");
                SattelitePage sattelitePage = new SattelitePage(estacao);
                sattelitePage.setVisible(true);
                MenuPage.this.dispose();
            }
        });

        sendMessageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Middleware middleware = new Middleware(dataQueue, estacao);
                MEM mem = new MEM("dados.csv");
                CPU thread = new CPU(dataQueue, mem, estacao.name);
                thread.start();
                MenuPage.this.dispose();
            }
        });

        JLabel lbTitle = new JLabel("Bem-vindo " + estacao.name);

        lbTitle.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bold +10");

        panel.add(lbTitle);
        panel.add(accessSatelliteButton, "gapy 10");
        panel.add(sendMessageButton, "gapy 3");

        add(panel);
    }
}
