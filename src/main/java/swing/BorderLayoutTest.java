package swing;
import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;
 
public class BorderLayoutTest extends JFrame{
	public static void main(String[]args) { 
		new BorderLayoutTest(); 
	}
	public BorderLayoutTest() {
		Container con=getContentPane();
		con.setLayout(new BorderLayout());
		con.add(new JButton(),BorderLayout.NORTH);
		con.add(new JButton(),BorderLayout.SOUTH);
		con.add(new JButton(),BorderLayout.WEST);
		con.add(new JButton(),BorderLayout.EAST);
		con.add(new JButton(),BorderLayout.CENTER);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(MAXIMIZED_BOTH);
		setVisible(true); 
	}
}
