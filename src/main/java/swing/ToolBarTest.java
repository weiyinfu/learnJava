package swing;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JToolBar;

public class ToolBarTest extends JFrame{
	public static void main(String[]args) {
		new ToolBarTest();
	}
	ActionListener listen=new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println(e.getActionCommand());
		}
	};
	public ToolBarTest() {
		Container container=getContentPane();
		container.setLayout(new BorderLayout());
		
		JToolBar toolBar=new JToolBar();
		JButton one=new JButton("one");
		JButton two=new JButton("two");
		one.addActionListener(listen);
		two.addActionListener(listen);
		toolBar.add(one);
		toolBar.add(two);
		add(toolBar,BorderLayout.NORTH);
		
		setSize(200, 200);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
}
