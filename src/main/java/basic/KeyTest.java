package basic;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyTest implements KeyListener {
public static void main(String[] args) {
    JFrame window = new JFrame("weidiao");
    window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    window.setSize(400, 400);
    window.setVisible(true);
    window.addKeyListener(new KeyTest());
}

@Override
public void keyPressed(KeyEvent arg0) {
    System.out.println(arg0.getKeyChar() + " is pressed");
}

@Override
public void keyReleased(KeyEvent arg0) {
    System.out.println(arg0.getKeyChar() + " is released");
}

@Override
public void keyTyped(KeyEvent arg0) {
    System.out.println(arg0.getKeyChar() + " is typed");
}
}
