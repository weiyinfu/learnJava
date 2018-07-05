package swing;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CalculatorDemo extends JFrame {
	public static void main(String[] args) {
		new CalculatorDemo();
	}

	JTextField problem = new JTextField();
	JTextField ans = new JTextField();
	int x, y, z;
	Random random = new Random();

	void init() {
		x = random.nextInt(90) + 10;
		y = random.nextInt(90) + 10;
		z = x * y;
		problem.setText(x + "\t" + y);
	}

	public CalculatorDemo() {
		setExtendedState(MAXIMIZED_BOTH);
		setLayout(new GridLayout(6, 1));
		add(problem);
		add(ans);
		problem.setEditable(false);
		ans.setEditable(false);
		Font font = new Font("Consolas", Font.BOLD, 30);
		problem.setFont(font);
		ans.setFont(font);
		for (int i = 0; i < 3; i++) {
			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(1, 3));
			for (int j = 0; j < 3; j++) {
				panel.add(getButton(i * 3 + j + 1));
			}
			add(panel);
		}
		add(getButton(0));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (ans.getText().length() == 0)
					return;
				if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
					ans.setText(ans.getText().substring(0, ans.getText().length() - 1));
				}
			}
		});
		init();
	}

	JButton getButton(int n) {
		JButton ret = new JButton(n + "");
		ret.addActionListener(listenAction);
		return ret;
	}

	ActionListener listenAction = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			ans.setText(ans.getText() + e.getActionCommand());
			if (ans.getText().equals(z + "")) {
				init();
				ans.setText("");
			}
			CalculatorDemo.this.requestFocus();
		}
	};
}
