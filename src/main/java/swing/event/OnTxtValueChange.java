package swing.event;

import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class OnTxtValueChange extends JFrame {
	public static void main(String[] args) {
		new OnTxtValueChange();
	}
	JTextArea area;
	public OnTxtValueChange() {
		setSize(600, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new GridLayout(3, 1));
		// ================================
		TextField textField = new TextField(20);
		add(textField);
		textField.addTextListener(new TextListener() {

			@Override
			public void textValueChanged(TextEvent e) {
				area.append("awt.TextField.textValueChanged\n");
			}
		});
		// ================================
		JTextField txt = new JTextField(20);
		add(txt);
		txt.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				area.append("JTextField.removeUpdate\n");
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				area.append("JTextFiedl.insertUpdate\n");
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				area.append("JTextField.changedUpdate");
			}
		});
		// ========================
		area = new JTextArea(10,10);
		area.setEditable(false);
		add(new JScrollPane(area));
		setVisible(true);
	}
}
