package swing.event;

import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

public class PopupMenuSample extends JFrame {
	public static void main(String[] args) {
		new PopupMenuSample();
	}

   PopupMenu menu;

	public PopupMenuSample() {
		setSize(300, 300);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		menu = new PopupMenu();
		menu.add(new MenuItem("one"));
		menu.add(new MenuItem("two"));
		menu.add(new MenuItem("three"));
		menu.add(new MenuItem("four"));
		add(menu);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == 3) {
					menu.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
		menu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(e.getActionCommand());
			}
		});
		setVisible(true);
	}
}