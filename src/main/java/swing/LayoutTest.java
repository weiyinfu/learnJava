package swing;
//package hartech.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class LayoutTest {
	private static JFrame jFrame, jFrame_detail;
	private static JPanel jPanel_detail, jPanel_main, jPanel_BorderLayout, jPanel_BoxLayout, jPanel_FlowLayout,
			jPanel_GridLayout, jPanel_GridBagLayout, jPanel_CardLayout, jPanel_Card_all, jPanel_Card_1, jPanel_Card_2,
			jPanel_Card_3, jPanel_null;
	private static JSplitPane jSplitPane, jSplitPane_up;
	private static JTextArea jTextArea_detail;
	private static JTabbedPane jTabbedPane;
	private static JComboBox<String> jComboBox_Card;
	private static String detail_string = "\nBorderLayout：\t共五区域，元件会自动充满，每个区域只可放一个元件/面板，一般用其中间区域（或再配合上或右区域）"
			+ "\n\t窗体变大时只要某一元件（主显示）变大，其它元件保持不变" + "\n\nFlowLayout：\t依次往行里添加（无向列添加），不够则换行（可多行），元件不会变大"
			+ "\n\t用于排队，变大时元件尽量往第一行插队，可多行" + "\n\nBoxLayout：\t全放置于单行或单列，不会换行/列（仅单行/列），元件不会变大，各元件可再设对齐方式"
			+ "\n\t用于仅是单行/列排队" + "\n\nGridLayout：\t建一每单元格大小相同的表格，元件充满表格" + "\n\t窗体变大时全部元件变大，各元件始终同大"
			+ "\n\nGridGagL..：\t仍是基于表格，但是元件可以跨多个格，行列宽度可以不同，元件可以不充满表格而是处于该格某角/边"
			+ "\n\t灵活度最高，窗体变大时全部元件按同比例变大，可设置任意大小，任意位置，类似于DW中表格"
			+ "\n\nCardLayout：\t在同一区域上显示不同面板，需再用其它元件来实现切换，可以选特定一个，也可以选*下/前一个*，*最先/后一个*"
			+ "\n\nTabbedPane：\t在同一区域上显示不同面板，实现更方便，可以选特定一个" + "\n\nSplitPane：\t划成两个区域（面板），可以完全关掉一区域，窗口变大时仅变大主区域（右/下）"
			+ "\n\t用于需要运行时可以随意改变区域大小分布比例，窗体变大时只要主显示变大" + "\n\nSpringLayout：\t可以在运行时自动调整各元件大小，如当新更新的数字过长无法全部显示时自动加长其所在区域"
			+ "\n\nnull Layout：\t可设置绝对位置，绝对大小，可重叠，不随窗口变化而变化"
			+ "\nJBoxLayout不能在构造函数中写，否则会抛出异常，试试就知道了，应该在setLayout()函数中写";

	public LayoutTest() {

		// jPanel_main
		jPanel_main();
		JLabel tabel = new JLabel("www.hartech.cn  ");
		tabel.setHorizontalAlignment(JLabel.RIGHT);

		// jFrame
		jFrame = new JFrame("Layouts Examples Show");
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// add
		jFrame.add(jPanel_main, BorderLayout.CENTER);
		jFrame.add(tabel, BorderLayout.SOUTH);
		jFrame.setSize(700, 300);
		jFrame.setVisible(true);
		dialog_detail();
	}

	public static JPanel jPanel_main() {

		// jTabbedPane
		jTabbedPane();
		jPanel_main = new JPanel(new BorderLayout());

		// add
		jPanel_main.add(jTabbedPane);
		return jPanel_main;
	}

	public static JTabbedPane jTabbedPane() {

		// different Layout panels
		jPanel_BorderLayout();
		jPanel_FlowLayout();
		jPanel_BoxLayout();
		jPanel_GridLayout();
		jPanel_GridBagLayout();
		jPanel_CardLayout();
		jSplitPane();
		jPanel_null();

		// jTabbedPane
		jTabbedPane = new JTabbedPane();

		// add
		// can add an icon or tip text
		jTabbedPane.addTab("BorderLayout", null, jPanel_BorderLayout, "BorderLayout tips");
		jTabbedPane.addTab("FlowLayout", jPanel_FlowLayout);
		jTabbedPane.addTab("BoxLayout", jPanel_BoxLayout);
		jTabbedPane.addTab("GridLayout", jPanel_GridLayout);
		jTabbedPane.addTab("GridBagLayout", jPanel_GridBagLayout);
		jTabbedPane.addTab("CardLayout", jPanel_CardLayout);
		jTabbedPane.addTab("SplitPane", jSplitPane);
		jTabbedPane.addTab("null", jPanel_null);

		jTabbedPane.setVisible(true);
		return jTabbedPane;
	}

	public static JPanel jPanel_BorderLayout() {
		// jPanel_BorderLayout

		// 设置元件间隙： BorderLayout(int 元件左右间隙,int 元件上下间隙)
		// new JPanel(new BorderLayout(10,30));
		// or void setHgap(int) void setVgap(int)
		jPanel_BorderLayout = new JPanel(new BorderLayout());

		// add
		// default is add to CENTER
		// if in the same location, one will cover the other
		jPanel_BorderLayout.add(new JButton("NORTH: ← →"), BorderLayout.NORTH);
		jPanel_BorderLayout.add(new JButton("WEST: ↑↓"), BorderLayout.WEST);
		jPanel_BorderLayout.add(new JButton("CENTER:  ← → ↑↓"), BorderLayout.CENTER);
		jPanel_BorderLayout.add(new JButton("EAST: ↑↓"), BorderLayout.EAST);
		jPanel_BorderLayout.add(new JButton("SOUTH: ← →"), BorderLayout.SOUTH);
		return jPanel_BorderLayout;
	}

	public static JPanel jPanel_FlowLayout() {
		// jPanel_FlowLayout
		// FlowLayout(对齐方式，元件左右间隙，元件上下间隙)
		// 默认为 中间对齐，左右上下各5间隙
		// Option: FlowLayout.CENTER LEFT RIGHT LEADING TRAILING
		jPanel_FlowLayout = new JPanel(new FlowLayout(FlowLayout.LEFT));

		// add
		jPanel_FlowLayout.add(new JButton("Flow 1"));
		jPanel_FlowLayout.add(new JButton("FlowFlow 2"));
		jPanel_FlowLayout.add(new JButton("FlowFlowFlow 3"));
		jPanel_FlowLayout.add(new JButton("FlowFlowFlowFlow 4"));
		jPanel_FlowLayout.add(new JButton("FlowFlow 5"));
		jPanel_FlowLayout.add(new JButton("Flow 6"));
		return jPanel_FlowLayout;
	}

	public static JPanel jPanel_BoxLayout() {
		// different way to achieve BoxLayout
		jPanel_BoxLayout = new JPanel();
		// Option: X_AXIS Y_AXIS LINE_AXIS PAGE_AXIS
		jPanel_BoxLayout.setLayout(new BoxLayout(jPanel_BoxLayout, BoxLayout.Y_AXIS));

		// add
		jPanel_BoxLayout.add(new JButton("Box"));
		jPanel_BoxLayout.add(new JButton("BoxBox"));
		// 改变对齐方向
		// if X_AXIS put Box.createHorizontalGlue()
		jPanel_BoxLayout.add(Box.createVerticalGlue());
		jPanel_BoxLayout.add(new JButton("BoxBoxBoxBoxBox"));
		jPanel_BoxLayout.add(new JButton("BoxBoxBox"));
		// 加入元件间隙（加入一空白矩形（宽，高））
		jPanel_BoxLayout.add(Box.createRigidArea(new Dimension(10, 0)));
		jPanel_BoxLayout.add(new JButton("Box"));
		return jPanel_BoxLayout;
	}

	public static JPanel jPanel_GridLayout() {
		// jPanel_GridLayout
		// GridLayout(行数，列数，元件左右间隙，上下间隙)
		// 默认为 单 行 n 列，间隙都为0
		jPanel_GridLayout = new JPanel(new GridLayout(3, 2));

		// add
		jPanel_GridLayout.add(new JButton("(3,2)"));
		jPanel_GridLayout.add(new JButton("Grid 2"));
		jPanel_GridLayout.add(new JButton("Grid 3"));
		jPanel_GridLayout.add(new JButton("Grid 4"));
		jPanel_GridLayout.add(new JButton("Grid 5"));
		jPanel_GridLayout.add(new JButton("Grid 6"));
		jPanel_GridLayout.add(new JButton("Grid 7"));
		return jPanel_GridLayout;
	}

	public static JPanel jPanel_GridBagLayout() {
		// jPanel_GridBagLayout
		jPanel_GridBagLayout = new JPanel(new GridBagLayout());

		// add
		// 每一元件使用一 GridBagConstraints 对象来设置其位置
		GridBagConstraints c = new GridBagConstraints();

		// fill 设置填充扩展方向，如 HORIZONTAL 为仅向左右扩张，VERTICAL BOTH
		c.fill = GridBagConstraints.BOTH;

		// 指示整个表格占面板的程度，0为全部最小挤到中间，1.0为撑满整个面板
		c.weightx = 1.0;
		c.weighty = 1.0;

		// gridx，gridy 设置元件处于哪列哪行
		c.gridx = 0;
		c.gridy = 0;
		jPanel_GridBagLayout.add(new JButton("one"), c);
		c.gridx = 1;
		c.gridy = 0;
		jPanel_GridBagLayout.add(new JButton("two"), c);
		c.gridx = 2;
		c.gridy = 0;
		jPanel_GridBagLayout.add(new JButton("three"), c);

		// ipadx ipady 设置该格宽度，高度，会影响到与其同行，同列的其它格宽高， 0 为默认
		c.ipadx = 150;
		c.ipady = 100;

		// gridwidth gridheight 跨格：该元件所占单元格，如下为占了 2x2 四个格
		c.gridwidth = 2;
		c.gridheight = 2;
		c.gridx = 0;
		c.gridy = 1;
		jPanel_GridBagLayout.add(new JButton("four"), c);

		// 恢复为默认
		c.ipadx = 0;
		c.ipady = 0;

		// request any extra vertical space
		c.weighty = 1.0;

		// 设该元件置于单元格哪角/边（若减去*间隙*（见下面）后仍有空间的话）
		c.anchor = GridBagConstraints.SOUTHEAST;

		// 元件与所在单元格各边的间隙的*最小值*
		// Insets(top,left,bottom,right)
		c.insets = new Insets(30, 60, 10, 5);

		c.gridx = 1;
		c.gridy = 3;
		jPanel_GridBagLayout.add(new JButton("five (30,60,10,5)"), c);

		return jPanel_GridBagLayout;
	}

	public static JPanel jPanel_CardLayout() {
		// comboBox
		String comboBoxItems[] = {

				"Card_1", "Card_2", "Card_3", "Next one", "Last one" };
		jComboBox_Card = new JComboBox<>(comboBoxItems);
		jComboBox_Card.setEditable(false);
		jComboBox_Card.addItemListener(new HItemListener());

		// cards panel
		jPanel_Card_all();

		// jPanel_CardLayout
		jPanel_CardLayout = new JPanel(new BorderLayout());
		jPanel_CardLayout.add(jComboBox_Card, BorderLayout.NORTH);
		jPanel_CardLayout.add(jPanel_Card_all, BorderLayout.CENTER);
		return jPanel_CardLayout;
	}

	// JComboBox ItemListener
	public static class HItemListener implements ItemListener {
		public void itemStateChanged(ItemEvent e) {

			// how to call a card out

			// use the card's string name
			CardLayout c = (CardLayout) (jPanel_Card_all.getLayout());

			if (((String) e.getItem()).equals("Next one")) {
				// next(Container) previous(Container)
				c.next(jPanel_Card_all);
			} else if (((String) e.getItem()).equals("Last one")) {
				// first(Container) last(Container)
				c.last(jPanel_Card_all);
			}
			// show(Container, String)
			c.show(jPanel_Card_all, (String) e.getItem());
		}
	}

	public static JPanel jPanel_Card_all() {
		jPanel_Card_1();
		jPanel_Card_2();
		jPanel_Card_3();

		// jPanel_Card_all
		// CardLayout(int hgap, int vgap)
		jPanel_Card_all = new JPanel(new CardLayout());
		// every card should give a string name , use to call it out
		jPanel_Card_all.add(jPanel_Card_1, "Card_1");
		jPanel_Card_all.add(jPanel_Card_2, "Card_2");
		jPanel_Card_all.add(jPanel_Card_3, "Card_3");
		return jPanel_Card_all;
	}

	public static JPanel jPanel_Card_1() {
		jPanel_Card_1 = new JPanel();
		jPanel_Card_1.add(new JLabel("Card_1 JPanel           "));
		jPanel_Card_1.add(new JButton("Button 1"));
		jPanel_Card_1.add(new JButton("Button 2"));
		jPanel_Card_1.add(new JButton("Button 3"));
		return jPanel_Card_1;
	}

	public static JPanel jPanel_Card_2() {
		jPanel_Card_2 = new JPanel();
		jPanel_Card_2.add(new JLabel("Card_2 JPanel           "));
		jPanel_Card_2.add(new JTextField("TextField", 20));
		return jPanel_Card_2;
	}

	public static JPanel jPanel_Card_3() {
		jPanel_Card_3 = new JPanel();
		jPanel_Card_3.add(new JLabel("Card_3 JPanel           "));
		jPanel_Card_3.add(new JLabel("JLabel: Ooop..........."));
		return jPanel_Card_3;
	}

	public static JSplitPane jSplitPane() {
		// jSplitPane_up
		jSplitPane_up();

		// jSplitPane
		// 本身就是一个面板
		// JSplitPane(水平/垂直分布，是否使用快速拉动模式，区域一，区域二)
		// JSplitPane.HORIZONTAL_SPLIT JSplitPane.VERTICAL_SPLIT
		jSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, jSplitPane_up(), new JButton("South button"));
		// 设置是否可以完全关掉一区域，（按分隔栏边的箭头）
		jSplitPane.setOneTouchExpandable(true);
		// 设置 左/上 区域的 宽/高 度，
		// -1为采用 左/上 区域的原 宽/高 度
		jSplitPane.setDividerLocation(150);
		return jSplitPane;
	}

	public static JSplitPane jSplitPane_up() {
		jSplitPane_up = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, new JButton("North Left button"),
				new JButton("North Right button"));
		jSplitPane_up.setOneTouchExpandable(true);
		jSplitPane_up.setDividerLocation(200);
		return jSplitPane_up;
	}

	public static JPanel jPanel_null() {
		// components
		JButton b1 = new JButton("one");
		JButton b2 = new JButton("two");
		JButton b3 = new JButton("three");

		Dimension size = b1.getPreferredSize();
		// setBounds(x坐标，y坐标，宽，高)
		b1.setBounds(50, 20, size.width, size.height);
		size = b2.getPreferredSize();
		b2.setBounds(300, 140, size.width + 200, size.height + 100);
		size = b3.getPreferredSize();
		b3.setBounds(520, 50, size.width, size.height + 150);

		// jPanel_null
		jPanel_null = new JPanel(null);
		jPanel_null.add(b1);
		jPanel_null.add(b2);
		jPanel_null.add(b3);
		return jPanel_null;
	}

	public static JFrame dialog_detail() {

		// panel_detail
		panel_detail();

		// JDialog
		jFrame_detail = new JFrame("Details");
		// jDialog = new JDialog(jFrame, "Details", false);
		jFrame_detail.add(jPanel_detail);
		jFrame_detail.pack();
		jFrame_detail.setVisible(true);
		return jFrame_detail;
	}

	public static JPanel panel_detail() {

		// JTextArea
		jTextArea_detail = new JTextArea(detail_string);
		jTextArea_detail.setEditable(false);

		// JPanel
		jPanel_detail = new JPanel(new BorderLayout());
		jPanel_detail.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// add
		jPanel_detail.add(jTextArea_detail);
		return jPanel_detail;
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new LayoutTest();
			}
		});
	}
}
