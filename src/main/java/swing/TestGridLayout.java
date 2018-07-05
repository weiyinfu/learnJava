package swing;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
public class TestGridLayout extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static void main(String[] args) {
		new TestGridLayout();
	}
	public TestGridLayout() {
		setLayout(new GridLayout(10, 10));
		setExtendedState(MAXIMIZED_BOTH);
		for (int i = 0; i < 100; i++) {
			add(new JButton(i + ""));
		}
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
}
