package swing.event;
import java.awt.BorderLayout; 
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
/* There are 3 method to bind keyEvent
 * ========
 * Component.setAccelerator(KeyStroke.getKeyStroke(‘Q’, InputEvent.CTRL_MASK));
 * ========
 * KeyStroke.getKeyStroke()
 * Component.getInputMap(int codition)
 * Component.getActionMap(int codition)
 * InputMap.put(KeyStroke,String)
 * ActionMap.put(String,ActionListener)
 * ========
 * JComponent.registerKeyBoardAction()
 * */
public class BindKey {
	static JTextArea area = new JTextArea(3, 30);
	static JTextField one = new JTextField(15);
	static JTextField two = new JTextField(15);

	public static void main(String[] args) {
		JFrame frame = new JFrame("press ctrl+enter to send message");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 400);

		frame.setLayout(new BorderLayout());
		frame.add(area, BorderLayout.CENTER);
		JPanel panel = new JPanel();
		panel.add(one);
		panel.add(two);
		frame.add(panel, BorderLayout.SOUTH);

		AbstractAction sendMsg = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				JTextField field = (JTextField) e.getSource();
				area.append(field.getText() + "\n");
				field.setText("");
			}
		};
		// 将Ctrl+Enter键和“send”关联
		one.getInputMap().put(KeyStroke.getKeyStroke('\n',
				java.awt.event.InputEvent.CTRL_MASK), "send");
		// 将"send"和sendMsg Action关联
		one.getActionMap().put("send", sendMsg);

		two.registerKeyboardAction(sendMsg, "send",
				KeyStroke.getKeyStroke('\n'), JComponent.WHEN_FOCUSED);
		frame.setVisible(true);
	}
}