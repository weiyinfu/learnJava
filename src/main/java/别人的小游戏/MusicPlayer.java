package 别人的小游戏;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.Vector;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

public class MusicPlayer extends JFrame implements Runnable {
public JLabel timeLabel;

private JSlider sldDiameter;
public AudioClip audioClip;
public JButton playButton, loopButton, stopButton;
public JButton addButton, deleteButton, saveButton, readButton;
public Vector<String> vector, mingcheng;
boolean fo = false;

public JPanel jp1, jp2, jp3, jp4;
JLabel jl1, jl2, sj1, sj2;
JTextField jt1, jt2;
public JList<String> jl;
JButton okButton, modifyButton;
int zong = 0;
int secondsPassed = 0, minutesPassed = 0, you = 0;
int minute, second;

public MusicPlayer() {
    super("java简单音乐播放器");
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    Container c = getContentPane();
    c.setLayout(new FlowLayout());

    jp1 = new JPanel();
    timeLabel = new JLabel();
    jp1.add(timeLabel);
    c.add(jp1);

    playButton = new JButton("开始播放");
    loopButton = new JButton("循环播放");
    stopButton = new JButton("停止播放");
    jp2 = new JPanel();
    jp2.add(playButton);
    jp2.add(loopButton);
    jp2.add(stopButton);
    c.add(jp2);

    jp4 = new JPanel();
    sj1 = new JLabel();
    sj2 = new JLabel();
    sldDiameter = new JSlider(SwingConstants.HORIZONTAL, 0, 100, 0);
    sldDiameter.setMajorTickSpacing(1);
    sldDiameter.setPaintTicks(true);
    jp4.add(sj1);
    jp4.add(sldDiameter);
    jp4.add(sj2);
    c.add(jp4);

    jl = new JList<>();
    jl.setVisibleRowCount(5);
    jl.setFixedCellHeight(40);
    jl.setFixedCellWidth(265);
    jl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    c.add(new JScrollPane(jl));

    addButton = new JButton("添加");
    deleteButton = new JButton("删除");
    readButton = new JButton("读取");
    saveButton = new JButton("保存");
    jp3 = new JPanel();
    jp3.add(addButton);
    jp3.add(deleteButton);
    jp3.add(saveButton);
    jp3.add(readButton);
    c.add(jp3);

    saveButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent event) {
            JFileChooser fileChooser = new JFileChooser(); // 实例化文件选择器
            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES); // 设置文件选择模式,此处为文件和目录均可
            if (fileChooser.showSaveDialog(MusicPlayer.this) == JFileChooser.APPROVE_OPTION) { // 弹出文件选择器,并判断是否点击了打开按钮
                String fileName = fileChooser.getSelectedFile().getAbsolutePath(); // 得到选择文件或目录的绝对路径
                mm(vector, mingcheng, fileName);
            }
        }
    });
    readButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent event) {
            JFileChooser fileChooser = new JFileChooser(); // 实例化文件选择器
            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES); // 设置文件选择模式,此处为文件和目录均可
            if (fileChooser.showOpenDialog(MusicPlayer.this) == JFileChooser.APPROVE_OPTION) { // 弹出文件选择器,并判断是否点击了打开按钮
                String fileName = fileChooser.getSelectedFile().getAbsolutePath(); // 得到选择文件或目录的绝对路径
                try {
                    ObjectInputStream input = new ObjectInputStream(new FileInputStream(fileName));
                    dizhi a1 = (dizhi) input.readObject();
                    mingcheng = a1.b;
                    vector = a1.a;
                    jl.setListData(mingcheng);
                    input.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    });
    playButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (jl.getSelectedIndex() >= 0) {
                String yy = (String) vector.get(jl.getSelectedIndex());
                try {
                    if (audioClip != null) {
                        audioClip.stop();
                        secondsPassed = 0;
                        minutesPassed = 0;
                        you = 0;
                    }
                    audioClip = Applet.newAudioClip(new File(yy).toURI().toURL());
                    fo = true;
                    sj2.setText(fen(yy));
                    audioClip.play();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            } else
                JOptionPane.showMessageDialog(null, "请选择音乐文件");
        }
    });
    loopButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (jl.getSelectedIndex() >= 0) {
                if (audioClip != null) {
                    audioClip.stop();
                    secondsPassed = 0;
                    minutesPassed = 0;
                    you = 0;
                }
                String yy = (String) vector.get(jl.getSelectedIndex());
                try {
                    audioClip = Applet.newAudioClip(new File(yy).toURI().toURL());
                } catch (MalformedURLException e1) {
                    e1.printStackTrace();
                }
                fo = true;
                sj2.setText(fen(yy));
                audioClip.loop();
            } else
                JOptionPane.showMessageDialog(null, "请选择音乐文件");
        }
    });
    stopButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            secondsPassed = 0;
            minutesPassed = 0;
            you = 0;
            fo = false;
            sldDiameter.setMaximum(100);
            sldDiameter.setValue(0);
            sj1.setText(null);
            sj2.setText(null);
            if (jl.getSelectedIndex() >= 0)
                audioClip.stop();
        }
    });
    addButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent event) {
            JFileChooser fileChooser = new JFileChooser(); // 实例化文件选择器
            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES); // 设置文件选择模式,此处为文件和目录均可
            fileChooser.setCurrentDirectory(new File(".")); // 设置文件选择器当前目录
            fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
                public boolean accept(File file) { // 可接受的文件类型
                    String name = file.getName().toLowerCase();
                    return name.endsWith(".wav") || name.endsWith(".au") || file.isDirectory();
                }

                public String getDescription() { // 文件描述
                    return "音乐文件(*.wav,*.au)";
                }
            });
            if (fileChooser.showOpenDialog(MusicPlayer.this) == JFileChooser.APPROVE_OPTION) { // 弹出文件选择器,并判断是否点击了打开按钮
                File file = fileChooser.getSelectedFile();
                vector.add(file.getAbsolutePath());
                mingcheng.add(file.getName());
            }
        }
    });
    deleteButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent event) {
            if (jl.getSelectedIndex() >= 0) {
                mingcheng.removeElementAt(jl.getSelectedIndex());
                vector.removeElementAt(jl.getSelectedIndex());
                jl.setListData(mingcheng);
            }
        }
    });
    jl.addMouseListener(new MouseAdapter() {
        public void mousePressed(MouseEvent event) {
            if (event.getClickCount() == 2) {
                if (jl.getSelectedIndex() >= 0) {
                    String yy = (String) vector.get(jl.getSelectedIndex());
                    File ff = new File(yy);
                    if (ff.exists()) {
                        if (yy.matches("[\\S\\s]*.wav"))
                            try {
                                if (audioClip != null) {
                                    audioClip.stop();
                                    secondsPassed = 0;
                                    minutesPassed = 0;
                                    you = 0;
                                }
                                audioClip = Applet.newAudioClip(new File(yy).toURI().toURL());
                                String a = fen(yy);
                                sj2.setText(a);
                                fo = true;
                                audioClip.play();
                            } catch (MalformedURLException e1) {
                                e1.printStackTrace();
                            }
                        else
                            JOptionPane.showMessageDialog(null, "此连接地址不是wav格式的文件,无法播放 建议删除");
                    } else
                        JOptionPane.showMessageDialog(null, "此歌曲文件已经不存在,建议删除");
                }
            }
            if (event.isMetaDown()) {
                if (jl.getSelectedIndex() >= 0) {
                    xiugai x = new xiugai();
                    x.setVisible(true);
                    jt1.setText(mingcheng.get(jl.getSelectedIndex()) + "");
                    jt2.setText(vector.get(jl.getSelectedIndex()) + "");
                }
            }
        }
    });
    addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    });
    setSize(300, 400);
    setVisible(true);
}

public void run() {
    while (true) {
        Date now = new Date();
        timeLabel.setText("当前时间: " + now.toString());
        if (fo) {
            secondsPassed++;
            you++;
            if (secondsPassed == second && minutesPassed == minute)
                fo = false;
            sldDiameter.setMaximum(zong);
            sldDiameter.setValue(you);
            if (secondsPassed >= 60) {
                minutesPassed++;
                secondsPassed = 0;
            }
            sj1.setText(minutesPassed + ":" + secondsPassed);
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public void mm(Vector<String> vector, Vector<String> mingcheng, String file) {
    dizhi a = new dizhi(vector, mingcheng);
    try {
        ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
        output.writeObject(a);
        output.flush();
        output.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

public String fen(String yy) {
    try {
        AudioFileFormat a = AudioSystem.getAudioFileFormat(new File(yy)); // 获取声音文件的映射
        double aaa = a.getFrameLength() / a.getFormat().getFrameRate(); // 获取声音文件是多少秒.
        zong = (int) aaa;
        minute = (int) aaa / 60;
        second = (int) aaa % 60;
        String ss = minute + ":" + second;
        return ss;
    } catch (IOException | UnsupportedAudioFileException e) {
        e.printStackTrace();
    }
    return null;
}

public static void main(String agrs[]) {
    MusicPlayer s = new MusicPlayer();
    Thread t1 = new Thread(s);
    t1.start();
}

class xiugai extends JFrame {
    public xiugai() {
        jl1 = new JLabel("文件名");
        jt1 = new JTextField(20);
        jl2 = new JLabel("文件路径");
        jt2 = new JTextField(20);
        // queding =new JButton("确定");
        modifyButton = new JButton("修改");
        Container c = getContentPane();
        c.setLayout(new GridLayout(3, 1));
        JPanel j1 = new JPanel();
        JPanel j2 = new JPanel();
        JPanel j3 = new JPanel();
        j1.add(jl1);
        j1.add(jt1);
        j2.add(jl2);
        j2.add(jt2);
        // j3.add(queding);
        j3.add(modifyButton);
        c.add(j1);
        c.add(j2);
        c.add(j3);
        modifyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                mingcheng.setElementAt(jt1.getText(), jl.getSelectedIndex());
                vector.setElementAt(jt2.getText(), jl.getSelectedIndex());
                jl.setListData(mingcheng);
                dispose();
            }
        });
        setSize(300, 120);
        setVisible(true);
    }
}


static class dizhi implements Serializable {
    Vector<String> a = new Vector<>();
    Vector<String> b = new Vector<>();

    public dizhi(Vector<String> vector, Vector<String> mingcheng) {
        a = vector;
        b = mingcheng;
    }
}
}