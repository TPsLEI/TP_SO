import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.LinkedBlockingQueue;

public class MenuPage extends BaseFrame {
    LinkedBlockingQueue<String> dataQueue = new LinkedBlockingQueue<>();

    public MenuPage(Estacao estacao) {
        super("Menu");
        setSize(450, 170);
        setResizable(false);

        JButton accessSatelliteButton = new JButton("Aceder ao Satélite");
        JButton sendMessageButton = new JButton("Enviar Mensagem");

        accessSatelliteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MEM.log("O Utilizador " + estacao.name + " acedeu ao satélite.");
                SattelitePage sattelitePage = new SattelitePage(estacao.name);
                sattelitePage.setVisible(true);
            }
        });

        sendMessageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Middleware middleware = new Middleware(dataQueue);
                MEM mem = new MEM("dados.csv");
                CPU thread = new CPU(dataQueue, mem, estacao.name);
                thread.start();
            }
        });

        setLayout(new BorderLayout());

        JLabel instructionLabel = new JLabel("Bem-vindo " + estacao.name);
        instructionLabel.setHorizontalAlignment(JLabel.LEFT);
        instructionLabel.setBorder(BorderFactory.createEmptyBorder(15, 15, 0, 0));
        instructionLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        add(instructionLabel, BorderLayout.NORTH);

        Box buttonBox = Box.createVerticalBox();
        buttonBox.add(Box.createVerticalGlue());

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(accessSatelliteButton);
        buttonPanel.add(sendMessageButton);

        buttonBox.add(buttonPanel);
        buttonBox.add(Box.createVerticalGlue());

        add(buttonBox, BorderLayout.CENTER);
    }

}
