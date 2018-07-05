package basic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.LineMetrics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

//为编写一些数字游戏做准备，如2048
public class DrawNumber extends JFrame {
private static final long serialVersionUID = 1L;

public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
        @Override
        public void run() {
            new DrawNumber();
        }
    });
}

public DrawNumber() {
    setTitle("DrawNumber");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setVisible(true);
    setSize(600, 600);
    addMouseListener(listenMouse);
}

int now = 0;

void draw(String s, Point p, Font font) {
    BufferedImage bit;
    Graphics2D g = (Graphics2D) getGraphics();
    Rectangle2D rec = font.getStringBounds(s,
            g.getFontRenderContext());
    LineMetrics metric = font.getLineMetrics(s,
            g.getFontRenderContext());
    bit = new BufferedImage((int) rec.getWidth(), (int) rec.getHeight(),
            BufferedImage.TYPE_INT_ARGB);
    Graphics2D gg = (Graphics2D) bit.getGraphics();
    gg.setColor(Color.RED);
    gg.setFont(font);
    gg.drawString(s, 0, metric.getAscent());
    g.clearRect(p.x, p.y, 200, 200);
    g.drawRect(p.x, p.y, 200, 200);
    g.drawImage(bit, p.x, p.y, 200, 200, null);
}

void draw() {
    Font font1 = new Font("Consolas", Font.BOLD, 100);
    Font font2 = new Font("楷体", Font.BOLD, 100);
    draw(Integer.toString(now), new Point(100, 100), font1);
    draw("天下第一", new Point(300, 300), font2);
}

MouseListener listenMouse = new MouseListener() {
    @Override
    public void mouseClicked(MouseEvent e) {
        now += 1;
        setTitle("Mouse Clicked");
        draw();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        now += 10;
        setTitle("mouseEntered");
        draw();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        now += 100;
        setTitle("mouseExited");
        draw();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        now += 1000;
        setTitle("mouse Pressed");
        draw();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        now += 10000;
        setTitle("mouseReleased");
        draw();
    }
};

}
