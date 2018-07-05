package swing;

import java.awt.Graphics;

import javax.swing.JWindow;

public class JWindowTest extends JWindow {
	public static void main(String[] args) {
		new JWindowTest();
	}

	public JWindowTest() { 
		setAlwaysOnTop(true);
		setSize(300, 300);
		setVisible(true);
	}

	public void paint(Graphics g) {
		g.fillRect(0, 0, getSize().width/2, getSize().height /2);
	}
}