import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class BaseFrame extends JFrame {

    public BaseFrame() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BorderLayout());

        // addLogo();
        setApplicationIcon();

        setLocationRelativeTo(null);
    }

    public BaseFrame(String title) {
        this();
        setTitle(title);
    }

    private void setApplicationIcon() {
        try {
            BufferedImage logoImage = ImageIO.read(getClass().getResource("logo/logo.png"));

            setIconImage(logoImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
