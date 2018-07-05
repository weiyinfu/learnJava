package 别人的小游戏;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

class 俄罗斯方块 extends JFrame {
public static void main(String[] args) {
    final JFrame frame = new JFrame("俄罗斯方块");
    final Block a = new Block();
    frame.addKeyListener(a);
    frame.add(a);
    final Timer timer = new Timer(400, a.new TimerListener());
    timer.start();
    JMenuBar menu = new JMenuBar();
    frame.setJMenuBar(menu);
    // 定义游戏菜单项
    JMenu gameMenu = new JMenu("游戏（G）");
    JMenuItem newitem = new JMenuItem("新游戏（N）");
    gameMenu.add(newitem);
    final JMenuItem pauseitem = new JMenuItem("暂停（P）");
    gameMenu.add(pauseitem);
    JMenuItem contitem = new JMenuItem("继续（c）");
    gameMenu.add(contitem);
    JMenuItem exititem = new JMenuItem("退出（E）");
    gameMenu.add(exititem);
    // 添加监听器来实现游戏菜单上的各个菜单项的功能,采用匿名内部类
    // 新游戏菜单项的功能实现
    newitem.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            a.newmap();
            a.drawwall();
            a.score = 0;
            a.x = 4;
            a.y = -1;
            a.blockType = a.ran.nextInt(7);
            a.turnState = a.ran.nextInt(4);
            a.nextb = a.ran.nextInt(7);
            a.nextt = a.ran.nextInt(4);
        }
    });
    // 暂停菜单项的功能实现
    pauseitem.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            timer.stop();
            pauseitem.setEnabled(false);
        }
    });
    // 继续菜单项的功能实现
    contitem.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            timer.start();
            pauseitem.setEnabled(true);
        }
    });
    // 退出菜单项的功能实现
    exititem.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            timer.stop();
            Object[] options = {"是的，我要退出", "不好意思，点错了"};
            int option = JOptionPane.showOptionDialog(null, "您确定要退出吗？", "退出提示....", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.WARNING_MESSAGE, null, options, options[0]);
            if (option == JOptionPane.OK_OPTION) {
                System.exit(0);
            }
            timer.start();
        }
    });
    // 定义帮助菜单项
    JMenu helpMenu = new JMenu("帮助（H）");
    JMenuItem aboutitem = new JMenuItem("关于游戏（G）");
    helpMenu.add(aboutitem);
    JMenuItem writeitem = new JMenuItem("关于作者（W）");
    helpMenu.add(writeitem);
    helpMenu.addSeparator();
    JMenuItem adviitem = new JMenuItem("游戏忠告（A）");
    helpMenu.add(adviitem);
    // 添加监听器来实现帮助菜单上的各个菜单项的功能,采用匿名内部类
    // 关于游戏菜单项的功能实现
    aboutitem.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            timer.stop();
            JOptionPane.showMessageDialog(frame,
                    "本游戏由孤独的野狼制作！\n如需要源代码，随时欢迎联系作者！\n"
                            + "作者邮箱：sunchuanhui212@126.com\nQQ号：2442701497\n本游戏功能基本上是齐全的\n"
                            + "并新增了“暂停”、“重新开始”等功能...\n希望您喜欢！\n" + "如有任何疑问及改善意见，随时欢迎指出。\n我们将尽最大的努力满足您的需求！\n"
                            + "最后谢谢您的使用！\n版权所有，请勿侵权！",
                    "关于游戏...", JOptionPane.INFORMATION_MESSAGE);
            timer.start();
        }
    });
    // 关于作者菜单项的功能实现
    writeitem.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            timer.stop();
            JOptionPane.showMessageDialog(frame,
                    "作者：孤独的野狼\n性别：男\n籍贯：湖南邵阳\n出生日：1990年11月9日\n" + "本科院校：上海应用技术学院\n现居地：上海\n自我介绍：不帅也不丑\n偶像：爱因斯坦\n"
                            + "最喜欢的歌手：刀郎\n最向往的地方：北京\n座右铭：疯狂源自梦想\n" + "勤奋铸就辉煌\n最喜欢的话：我愿变成一座石桥，受五百年风吹，五百年雨打，\n"
                            + "五百年日晒，只求你从上面走过...\n" + "梦想：天地有多大，梦有多潇洒\n",
                    "关于作者...", JOptionPane.INFORMATION_MESSAGE);
            timer.start();
        }
    });
    // 游戏忠告菜单项的功能实现
    adviitem.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            timer.stop();
            JOptionPane.showMessageDialog(frame,
                    "抵制不良游戏 , 拒绝盗版游戏\n\n注意自我保护 , 谨防受骗上当\n\n" + "适度游戏益脑 , 沉迷游戏伤身\n\n合理安排时间 , 享受健康生活\n", "健康游戏忠告。。。",
                    JOptionPane.INFORMATION_MESSAGE);
            timer.start();
        }
    });
    menu.add(gameMenu);
    menu.add(helpMenu);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setBounds(300, 100, 400, 520);
    frame.setVisible(true);
    frame.setResizable(false);
}


// 创建一个俄罗斯方块类
static class Block extends JPanel implements KeyListener {
    private static final long serialVersionUID = 1L;
    Random ran = new Random(); // 使用Random函数；
    public int blockType; // blockType代表方块类型
    public int score = 0; // 分数；
    public int turnState; // turnState代表方块状态
    public int x; // 方块起始位置的横坐标
    public int y; // 方块起始位置的纵坐标
    public int nextb = ran.nextInt(7); // 下一个方块类型；
    public int nextt = ran.nextInt(4); // 下一个方块的形状；
    private int i = 0;
    private int j = 0;
    private boolean flag = false;
    int[][] map = new int[13][23];

    Block() {
        newblock();
        newmap();
        drawwall();
    }

    // 第一组代表方块的形状，方块形状类型有S、Z、L、J、I、O、T 7种
    // 第二组代表方块旋转几次
    // 第三四组为方块矩阵
    private final int shapes[][][] = new int[][][]{
            // 棒型方块
            {{0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0},
                    {0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0}},
            // s型方块
            {{0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
                    {0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0}},
            // z型方块
            {{1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
                    {1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0}},
            // 右l型方块
            {{0, 1, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0}, {1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
                    {1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}},
            // 田型方块
            {{1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}},
            // 左l型方块
            {{1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0}, {1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {1, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
                    {0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}},
            // t型方块
            {{0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
                    {1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0}}};

    // 生成新方块的方法
    public void newblock() {
        blockType = nextb;
        turnState = nextt;
        nextb = ran.nextInt(7);
        nextt = ran.nextInt(4);
        x = 4;
        y = 0;
        if (gameover(x, y)) {
            JOptionPane.showMessageDialog(null, "不好意思，游戏结束，请再接再厉！");
            newmap();
            drawwall();
            score = 0;
        }
    }

    // 判断游戏结束的方法
    public boolean gameover(int x, int y) {
        if (blow(x, y, blockType, turnState)) {
            return true;
        } else {
            return false;
        }
    }

    // 是否合法的方法
    public boolean blow(int x, int y, int blockType, int turnState) {
        for (int a = 0; a < 4; a++) {
            for (int b = 0; b < 4; b++) {
                if (((shapes[blockType][turnState][a * 4 + b] == 1) && (map[x + b + 1][y + a] == 1))
                        || ((shapes[blockType][turnState][a * 4 + b] == 1) && (map[x + b + 1][y + a] == 2))) {
                    return true;
                }
            }
        }
        return false;
    }

    // 初始化地图
    public void newmap() {
        for (i = 0; i < 12; i++) {
            for (j = 0; j < 22; j++) {
                map[i][j] = 0;
            }
        }
    }

    // 画围墙
    public void drawwall() {
        for (i = 0; i < 12; i++) {
            map[i][21] = 2;
        }
        for (j = 0; j < 22; j++) {
            map[11][j] = 2;
            map[0][j] = 2;
        }
    }

    // 旋转的方法
    public void turn() {
        int tempturnState = turnState;
        turnState = (turnState + 1) % 4;
        if (!blow(x, y, blockType, turnState)) {
        }
        if (blow(x, y, blockType, turnState)) {
            turnState = tempturnState;
        }
        repaint();
    }

    // 左移的方法
    public void left() {
        if (!blow(x - 1, y, blockType, turnState)) {
            x = x - 1;
        }
        repaint();
    }

    // 右移的方法
    public void right() {
        if (!blow(x + 1, y, blockType, turnState)) {
            x = x + 1;
        }
        repaint();
    }

    // 下落的方法
    public void down() {
        if (!blow(x, y + 1, blockType, turnState)) {
            y = y + 1;
        }
        if (blow(x, y + 1, blockType, turnState)) {
            add(x, y, blockType, turnState);
            newblock();
        }
        delline();
        repaint();
    }

    // 消行的方法
    public void delline() {
        int c = 0;
        int i = 0;
        // i用来确定本次消了几行
        for (int b = 0; b < 22; b++) {
            for (int a = 0; a < 12; a++) {
                if (map[a][b] == 1) {
                    c = c + 1;
                    if (c == 10) {
                        i = i + 1;
                        for (int d = b; d > 0; d--) {
                            for (int e = 0; e < 11; e++) {
                                map[e][d] = map[e][d - 1];
                            }
                        }
                    }
                }
            }
            c = 0;
        }
        // 确定消行分数
        switch (i) {
            case 1:
                score = score + 1;
                break;
            case 2:
                score = score + 3;
                break;
            case 3:
                score = score + 6;
                break;
            case 4:
                score = score + 10;
                break;
            default:
                break;
        }
    }

    // 把当前添加map
    public void add(int x, int y, int blockType, int turnState) {
        int j = 0;
        for (int a = 0; a < 4; a++) {
            for (int b = 0; b < 4; b++) {
                if (map[x + b + 1][y + a] == 0) {
                    map[x + b + 1][y + a] = shapes[blockType][turnState][j];
                }
                j++;
            }
        }
    }

    // 画方块的的方法
    public void paintComponent(Graphics g) {
        // Object[] yanse = {"BLUE","GREEN","LIGHT_GRAY","YELLOW","PINK"};
        super.paintComponent(g);
        // 画当前方块
        for (j = 0; j < 16; j++) {
            if (shapes[blockType][turnState][j] == 1) {
                // 画矩形区域
                g.setColor(Color.BLUE);
                g.fill3DRect((j % 4 + x + 1) * 20, (j / 4 + y) * 20, 20, 20, true);
                g.setColor(Color.BLACK);
                g.draw3DRect((j % 4 + x + 1) * 20, (j / 4 + y) * 20, 20, 20, true);
            }
        }
        // 画下一个方块
        for (j = 0; j < 16; j++) {
            if (shapes[nextb][nextt][j] == 1) {
                g.setColor(Color.BLUE);
                g.fill3DRect((j % 4 + 1) * 20 + 250, (j / 4) * 20 + 40, 20, 20, true);
                g.setColor(Color.BLACK);
                g.draw3DRect((j % 4 + 1) * 20 + 250, (j / 4) * 20 + 40, 20, 20, true);
            }
        }
        // 画已经固定的方块
        for (j = 0; j < 22; j++) {
            for (i = 0; i < 12; i++) {
                if (map[i][j] == 2) { // 画围墙
                    g.setColor(Color.BLACK);
                    g.fill3DRect(i * 20, j * 20, 20, 20, true);
                    g.setColor(Color.WHITE);
                    g.draw3DRect(i * 20, j * 20, 20, 20, true);
                }
                if (map[i][j] == 1) { // 画固定的方块
                    g.setColor(Color.GREEN);
                    g.fill3DRect(i * 20, j * 20, 20, 20, true);
                    g.setColor(Color.BLACK);
                    g.draw3DRect(i * 20, j * 20, 20, 20, true);
                }
            }
        }
        g.setColor(Color.black);
        g.drawString("score = " + score, 250, 10);
        g.setColor(Color.RED);
        g.drawString("下一个方块：", 250, 30);
    }

    // 键盘监听
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_DOWN:
                down();
                break;
            case KeyEvent.VK_SPACE:
                turn();
                break;
            case KeyEvent.VK_RIGHT:
                right();
                break;
            case KeyEvent.VK_LEFT:
                left();
                break;
            default:
                break;
        }
    }

    // 保留，以备升级用
    public void keyReleased(KeyEvent e) {
    }

    // 保留，以备升级用
    public void keyTyped(KeyEvent e) {
    }

    // 定时器监听
    class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            repaint();
            if (!blow(x, y + 1, blockType, turnState)) {
                y = y + 1;
                delline();
            }
            if (blow(x, y + 1, blockType, turnState)) {
                if (flag == true) {
                    add(x, y, blockType, turnState);
                    delline();
                    newblock();
                    flag = false;
                }
                flag = true;
            }
        }
    }
}
}