package swing.specialFrame;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.geom.Ellipse2D;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;

public class ShapeWindow extends JFrame {
	public ShapeWindow() {
		super("不规则窗体");
		this.setLayout(new FlowLayout());
		this.add(new JButton("按钮"));
		this.add(new JCheckBox("复选按钮"));
		this.add(new JRadioButton("单选按钮"));
		this.add(new JProgressBar(0, 100));

		this.setSize(new Dimension(400, 400));
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Window w = new ShapeWindow();
				w.setVisible(true);
				com.sun.awt.AWTUtilities.setWindowShape(w, new Ellipse2D.Double(0, 0, w.getWidth(), w.getHeight()));
				com.sun.awt.AWTUtilities.setWindowOpacity(w, 0.93f);
			}
		});
	}
}