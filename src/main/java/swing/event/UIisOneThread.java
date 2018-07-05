package swing.event;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class UIisOneThread extends JFrame {
	public static void main(String args[]) {
		new UIisOneThread();
	}

	JLabel label = new JLabel();

	UIisOneThread() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(300, 300);
		setLayout(new BorderLayout());
		JButton a = new JButton("a");
		add(a, BorderLayout.NORTH);
		JButton b = new JButton("b");
		add(b, BorderLayout.CENTER);
		add(label, BorderLayout.SOUTH);
		a.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					setTitle("");
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		});
		b.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setTitle("b is clicked");
			}
		});
		setVisible(true);
		int i = 0;
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			label.setText(i+++" seconds passed");
		}
	}
}