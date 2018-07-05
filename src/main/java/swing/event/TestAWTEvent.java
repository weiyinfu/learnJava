package swing.event;

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;

import javax.swing.JFrame;

public class TestAWTEvent {
	public static void main(String[] args) {
		JFrame frame = new JFrame("gaga");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(100, 100);
		frame.setVisible(true);
		Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {

			@Override
			public void eventDispatched(AWTEvent event) {
				System.out.println(event.paramString());
			}
		}, AWTEvent.KEY_EVENT_MASK);
	}
}