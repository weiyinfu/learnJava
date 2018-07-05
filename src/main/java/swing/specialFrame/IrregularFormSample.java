package swing.specialFrame;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.sun.awt.AWTUtilities;

public class IrregularFormSample extends JFrame {
	private Point origin; // 用于移动窗体
	private BufferedImage img; // 用来设定窗体不规则样式的图片

	public IrregularFormSample() {
		try {
			img = ImageIO.read(new File("twitter.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		// 设定窗体大小和图片一样大
		this.setSize(img.getWidth(), img.getHeight());
		this.setUndecorated(true);
		this.origin = new Point();

		AWTUtilities.setWindowShape(this, getImageShape(img));
		AWTUtilities.setWindowOpacity(this, 1.0f);

		this.setLocationRelativeTo(null);

		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				origin.x = e.getX();
				origin.y = e.getY();
			}

			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3)
					System.exit(0);
			}
		});

		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				Point p = getLocation();
				setLocation(p.x + e.getX() - origin.x, p.y + e.getY() - origin.y);
			}
		});
	}

	public Shape getImageShape(BufferedImage img) {
		ArrayList<Integer> x = new ArrayList<Integer>();
		ArrayList<Integer> y = new ArrayList<Integer>();
		int width = img.getWidth(null);// 图像宽度
		int height = img.getHeight(null);// 图像高度

		// 筛选像素
		// 首先获取图像所有的像素信息
		PixelGrabber pgr = new PixelGrabber(img, 0, 0, -1, -1, true);
		try {
			pgr.grabPixels();
		} catch (InterruptedException ex) {
			ex.getStackTrace();
		}
		int pixels[] = (int[]) pgr.getPixels();

		// 循环像素
		for (int i = 0; i < pixels.length; i++) {
			// 筛选，将不透明的像素的坐标加入到坐标ArrayList x和y中
			int alpha = getAlpha(pixels[i]);
			if (alpha == 0) {
				continue;
			} else {
				x.add(i % width > 0 ? i % width - 1 : 0);
				y.add(i % width == 0 ? (i == 0 ? 0 : i / width - 1) : i / width);
			}
		}

		// 建立图像矩阵并初始化(0为透明,1为不透明)
		int[][] matrix = new int[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				matrix[i][j] = 0;
			}
		}

		// 导入坐标ArrayList中的不透明坐标信息
		for (int c = 0; c < x.size(); c++) {
			matrix[y.get(c)][x.get(c)] = 1;
		}

		/*
		 * 由于Area类所表示区域可以进行合并，我们逐一水平"扫描"图像矩阵的每一行，
		 * 将不透明的像素生成为Rectangle，再将每一行的Rectangle通过Area类的rec
		 * 对象进行合并，最后形成一个完整的Shape图形
		 */
		Area rec = new Area();
		int temp = 0;

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (matrix[i][j] == 1) {
					if (temp == 0)
						temp = j;
					else if (j == width) {
						if (temp == 0) {
							Rectangle rectemp = new Rectangle(j, i, 1, 1);
							rec.add(new Area(rectemp));
						} else {
							Rectangle rectemp = new Rectangle(temp, i, j - temp, 1);
							rec.add(new Area(rectemp));
							temp = 0;
						}
					}
				} else {
					if (temp != 0) {
						Rectangle rectemp = new Rectangle(temp, i, j - temp, 1);
						rec.add(new Area(rectemp));
						temp = 0;
					}
				}
			}
			temp = 0;
		}
		return rec;
	}

	private int getAlpha(int pixel) {
		return (pixel >> 24) & 0xff;
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(img, 0, 0, null);
	}

	public static void main(String[] args) {
		IrregularFormSample sample = new IrregularFormSample();
		sample.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		sample.setVisible(true);
		sample.repaint();
	}
}