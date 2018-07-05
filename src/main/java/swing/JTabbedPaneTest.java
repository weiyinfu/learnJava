package swing;

import java.awt.Button;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class JTabbedPaneTest extends JFrame {
	public static void main(String[] args) {
		new JTabbedPaneTest();
	}

	public JTabbedPaneTest() {
		JTabbedPane tab = new JTabbedPane();
		tab.setTabPlacement(JTabbedPane.LEFT);
		tab.addTab("one", new Button("one"));
		tab.addTab("two", new Button("two"));
		tab.addTab("three", new Button("three"));
		add(tab);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(400, 400);
		setVisible(true);
	}
}
