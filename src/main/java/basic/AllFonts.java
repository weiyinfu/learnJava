package basic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * 展示系统上的全部字体
 */
public class AllFonts extends JFrame {
public static void main(String[] args) {
    GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
    String[] fonts = e.getAvailableFontFamilyNames();
    for (String s : fonts)
        System.out.println(s);
    font = e.getAllFonts();
    new AllFonts();
}

AllFonts() {
    addKeyListener(listen);
    setSize(800, 600);
    setVisible(true);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
}

KeyAdapter listen = new KeyAdapter() {
    @Override
    public void keyPressed(KeyEvent g) {
        if (g.getKeyCode() == KeyEvent.VK_RIGHT) {
            which++;
            if (which == font.length)
                which = 0;
        } else if (g.getKeyCode() == KeyEvent.VK_LEFT) {
            which--;
            if (which == -1)
                which = font.length - 1;
        }
        repaint();
    }
};
String s = "weidiao魏印福";
static Font[] font;
static Font f;
int which = 0;

public void paint(Graphics g) {
    g.setColor(Color.gray);
    g.fillRect(0, 0, getWidth(), getHeight());
    f = new Font(font[which].getName(), Font.BOLD, 30);
    BufferedImage pic = getPic(s);
    setTitle("width:" + pic.getWidth() + " height:" + pic.getHeight() + " index" + which + " fontName : " + font[which].getName());
    g.drawImage(pic, 100, 100, null);
    g.setColor(Color.blue);
}

BufferedImage getPic(String s) {
    BufferedImage bit;
    Rectangle2D rec = getFontMetrics(f).getStringBounds(s, getGraphics());
    bit = new BufferedImage((int) rec.getWidth() + 1, 1 + (int) rec.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
    Graphics gg = bit.getGraphics();
    gg.setColor(Color.black);
    gg.fillRect(0, 0, bit.getWidth(), bit.getHeight());
    gg.setFont(f);
    gg.setColor(Color.RED);
    gg.drawString(s, 0, getFontMetrics(f).getAscent());
    return bit;
}
}