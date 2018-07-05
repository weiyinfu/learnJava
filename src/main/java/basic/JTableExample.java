package basic;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class JTableExample extends JFrame {
private JTable table;

public JTableExample() {
    setExtendedState(MAXIMIZED_BOTH);
    Integer[][] a = new Integer[10][4];
    Random r = new Random();
    for (int i = 0; i < a.length; i++) {
        a[i][3] = 0;
        for (int j = 0; j < 3; j++) {
            a[i][j] = r.nextInt();
            a[i][3] += a[i][j];
        }
    }
    table = new JTable(a, new String[]{"chinese", "english", "math", "sum"});
    table.setFont(new Font("AR PL UKai CN", Font.BOLD, 30));
    getContentPane().add(table);
    pack();
    setVisible(true);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
}

public static void main(String[] args) {
    new JTableExample();
}
}