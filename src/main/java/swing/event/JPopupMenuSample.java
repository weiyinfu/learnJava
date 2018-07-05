package swing.event;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

public class JPopupMenuSample {
	static class PopupActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("Selected: " + event.getActionCommand());
		}
	}

	static class MyPopupMenuListener implements PopupMenuListener {
		public void popupMenuCanceled(PopupMenuEvent event) {
			System.out.println("Canceled");
		}

		public void popupMenuWillBecomeInvisible(PopupMenuEvent event) {
			System.out.println("Becoming Invisible");
		}

		public void popupMenuWillBecomeVisible(PopupMenuEvent event) {
			System.out.println("Becoming Visible");
		}
	}

	public static void main(String[] args) {
		Runnable runner = new Runnable() {
			public void run() { 
				JFrame frame = new JFrame("PopupMenu Sample");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				ActionListener acitonListener = new PopupActionListener();
				PopupMenuListener popupMenuListener = new MyPopupMenuListener();

				// Create popup menu, attach popup menu listener
				JPopupMenu popupMenu = new JPopupMenu("Title");
				popupMenu.addPopupMenuListener(popupMenuListener);

				// Cut
				JMenuItem cutMenuItem = new JMenuItem("Cut");
				cutMenuItem.addActionListener(acitonListener);
				popupMenu.add(cutMenuItem);

				// Copy
				JMenuItem copyMenuItem = new JMenuItem("Copy");
				copyMenuItem.addActionListener(acitonListener);
				popupMenu.add(copyMenuItem);

				// Paste
				JMenuItem pasteMenuItem = new JMenuItem("Paste");
				pasteMenuItem.addActionListener(acitonListener);
				popupMenu.add(pasteMenuItem);

				// Separator
				popupMenu.addSeparator();

				// Find
				JMenuItem findMenuItem = new JMenuItem("Find");
				findMenuItem.addActionListener(acitonListener);
				popupMenu.add(findMenuItem);

				JButton label = new JButton();
				frame.add(label);
				label.setComponentPopupMenu(popupMenu);

				frame.setSize(350, 250);
				frame.setVisible(true);
			}
		};

		EventQueue.invokeLater(runner);
	}

}