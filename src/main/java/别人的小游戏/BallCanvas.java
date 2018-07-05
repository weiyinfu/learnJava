package 别人的小游戏;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class BallCanvas extends Canvas implements Runnable {
public static void main(String[] args) {
    JFrame window = new JFrame("two ball collide");
    window.setSize(500, 500);
    BallCanvas canva = new BallCanvas(500, 500);
    window.add(canva);
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setVisible(true);
    Timer t = new Timer();
    t.schedule(new TimerTask() {

        @Override
        public void run() {
            canva.run();
        }
    }, 0, 10);
}

private int ballRadius = 10;
private int ballAX, ballAY;
private int ballBX, ballBY;

private double ballAXMoveLength = 7;
private double ballAYMoveLength = 100;
private double ballBXMoveLength = 8;
private double ballBYMoveLength = 9;
private Random r;
private boolean move = true;
private int screenWidth, screenHeight;
private int ballARX, ballARY;
private int ballBRX, ballBRY;

public BallCanvas(int screenWidth, int screenHeight) {

    r = new Random();

    ballAX = r.nextInt(screenWidth - 2 * ballRadius);
    ballAY = r.nextInt(screenHeight - 2 * ballRadius);

    do {
        ballBX = r.nextInt(screenWidth - 2 * ballRadius);
        ballBY = r.nextInt(screenHeight - 2 * ballRadius);
    } while ((int) Math.sqrt((ballAX - ballBX) * (ballAX - ballBX)
            + (ballAY - ballBY) * (ballAY - ballBY)) < 2 * ballRadius);

    this.screenHeight = screenHeight;
    this.screenWidth = screenWidth;
}

public void paint(Graphics g) {
    BufferedImage bit = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
    Graphics gg = bit.getGraphics();
    gg.setColor(Color.RED);
    gg.fillArc((int) ballAX, (int) ballAY, (int) ballRadius * 2,
            (int) ballRadius * 2, 0, 360);
    gg.fillArc((int) ballBX, (int) ballBY, (int) ballRadius * 2,
            (int) ballRadius * 2, 0, 360);
    g.drawImage(bit, 0, 0, null);
}

public void exit() {
    move = false;
}

public void run() {
    while (move) {
        if (ballAX + ballAXMoveLength + 2 * ballRadius > screenWidth
                || ballAX + ballAXMoveLength < 0) {
            ballAXMoveLength *= -1;
        } else {
            ballAX += ballAXMoveLength;
        }
        if (ballAY + ballAYMoveLength + 2 * ballRadius > screenHeight
                || ballAY + ballAYMoveLength < 0) {
            ballAYMoveLength *= -1;
        } else {
            ballAY += ballAYMoveLength;
        }
        if (ballBX + ballBXMoveLength + 2 * ballRadius > screenWidth
                || ballBX + ballBXMoveLength < 0) {
            ballBXMoveLength *= -1;
        } else {
            ballBX += ballBXMoveLength;
        }
        if (ballBY + ballBYMoveLength + 2 * ballRadius > screenHeight
                || ballBY + ballBYMoveLength < 0) {
            ballBYMoveLength *= -1;
        } else {
            ballBY += ballBYMoveLength;
        }
        ballsCollide();
        repaint();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {

        }
    }
}

public void ballsCollide() {
    if (Math.sqrt((ballAX - ballBX) * (ballAX - ballBX) + (ballAY - ballBY) * (ballAY - ballBY)) > 2 * ballRadius) {
        return;
    } else {
        ballARX = ballAX + ballRadius;
        ballARY = ballAY + ballRadius;
        ballBRX = ballBX + ballRadius;
        ballBRY = ballBY + ballRadius;
        double sx = ballARX - ballBRX;
        double sy = ballARY - ballBRY;
        double s1x = sx / Math.sqrt(sx * sx + sy * sy);
        double s1y = sy / Math.sqrt(sx * sx + sy * sy);
        double tx = -sy;
        double ty = sx;
        double t1x = tx / Math.sqrt(tx * tx + ty * ty);
        double t1y = ty / Math.sqrt(tx * tx + ty * ty);

        double v1s = ballAXMoveLength * s1x + ballAYMoveLength * s1y;
        double v1t = ballAXMoveLength * t1x + ballAYMoveLength * t1y;
        double v2s = ballBXMoveLength * s1x + ballBYMoveLength * s1y;
        double v2t = ballBXMoveLength * t1x + ballBYMoveLength * t1y;
        double v1sf = v2s;
        double v2sf = v1s;
        double nsx = v1sf * s1x;
        double nsy = v1sf * s1y;
        double ntx = v1t * t1x;
        double nty = v1t * t1y;

        ballAXMoveLength = (nsx + ntx);
        ballAYMoveLength = (nsy + nty);
        nsx = v2sf * s1x;
        nsy = v2sf * s1y;
        ntx = v2t * t1x;
        nty = v2t * t1y;

        ballBXMoveLength = (nsx + ntx);
        ballBYMoveLength = (nsy + nty);

        while (Math.sqrt((ballAX - ballBX) * (ballAX - ballBX) + (ballAY - ballBY) * (ballAY - ballBY)) < 2 * ballRadius) {
            if (ballAX + ballAXMoveLength + 2 * ballRadius > screenWidth
                    || ballAX + ballAXMoveLength < 0) {
                ballAXMoveLength *= -1;
            } else {
                ballAX += ballAXMoveLength;
            }
            if (ballAY + ballAYMoveLength + 2 * ballRadius > screenHeight
                    || ballAY + ballAYMoveLength < 0) {
                ballAYMoveLength *= -1;
            } else {
                ballAY += ballAYMoveLength;
            }
            if (ballBX + ballBXMoveLength + 2 * ballRadius > screenWidth
                    || ballBX + ballBXMoveLength < 0) {
                ballBXMoveLength *= -1;
            } else {
                ballBX += ballBXMoveLength;
            }
            if (ballBY + ballBYMoveLength + 2 * ballRadius > screenHeight
                    || ballBY + ballBYMoveLength < 0) {
                ballBYMoveLength *= -1;
            } else {
                ballBY += ballBYMoveLength;
            }
        }
    }

}

public void canvasResize() {
    screenWidth = this.getWidth();
    screenHeight = this.getHeight();
}
}
