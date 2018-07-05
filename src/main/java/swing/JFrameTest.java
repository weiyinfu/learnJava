package swing;

import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JFrame;

public class JFrameTest extends JFrame {
	public static void main(String[] args) {
		new JFrameTest();
	}

	public JFrameTest() {
		setSize(300, 300);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setUndecorated(true);
		setOpacity(0.5f);
		setVisible(true);
		addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				double x = (getOpacity() + e.getWheelRotation() / 10.0);
				if (x > 1)
					x = 1;
				if (x < 0)
					x = 0;
				System.out.println(x);
				setOpacity((float) x);
			}
		});
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
					System.exit(0);
			}
		});
	}

	@Override
	public void paint(Graphics g) {
		g.fillRect(0, 0, 30, 30);
	}
}
