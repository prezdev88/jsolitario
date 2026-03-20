package util;

import java.awt.Color;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 *
 * @author Pato
 */
public class UiUtils {

    private static final int WIDTH_PADDING = 20;
    private static final int HEIGHT_PADDING = 50;

    public static void setFrameIcon(JFrame frame, String iconPath) {
        URL iconUrl = frame.getClass().getResource(iconPath);
        ImageIcon icon = new ImageIcon(iconUrl);
        frame.setIconImage(icon.getImage());
    }

    public static void resizeFrame(JFrame frame, boolean resizable, String title) {
        int width = (int) frame.getPreferredSize().getWidth() + WIDTH_PADDING;
        int height = (int) frame.getPreferredSize().getHeight() + HEIGHT_PADDING;
        frame.setBounds(0, 0, width, height);
        frame.setLocationRelativeTo(null);
        frame.setResizable(resizable);
        frame.setTitle(title);
    }

    public static void setLabelImage(JFrame frame, JLabel label, String imagePath) {
        URL imageUrl = frame.getClass().getResource(imagePath);
        ImageIcon image = new ImageIcon(imageUrl);
        label.setIcon(image);
        label.setHorizontalAlignment(SwingConstants.CENTER);
    }

    public static void setLabelBackground(JLabel label, Color color) {
        label.setBackground(color);
    }
}
