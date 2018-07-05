package swing;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class JLookAndFeelMenu extends JMenu {
	UIManager.LookAndFeelInfo[] info = UIManager.getInstalledLookAndFeels();
	JFrame father;

	public JLookAndFeelMenu(JFrame father) {
		super("look and feel");
		this.father = father;
		ButtonGroup buttonGroup = new ButtonGroup();
		for (int i = 0; i < info.length; i++) {
			JRadioButtonMenuItem item = new JRadioButtonMenuItem(info[i].getClassName());
			buttonGroup.add(item);
			add(item);
			item.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						UIManager.setLookAndFeel(e.getActionCommand());
						SwingUtilities.updateComponentTreeUI(JLookAndFeelMenu.this.father);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});
		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 300);
		JMenuBar bar = new JMenuBar();
		JMenu menu = new JLookAndFeelMenu(frame);
		bar.add(menu);
		frame.getContentPane().add(BorderLayout.NORTH, bar);
		frame.getContentPane().add(BorderLayout.CENTER, new JButton("Hello"));
		frame.setVisible(true);
	}
}