package graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.util.Calendar;

public class Clock extends JPanel implements ActionListener {
Ellipse2D face = new Ellipse2D.Float(3, 3, 94, 94);
Ellipse2D pivot = new Ellipse2D.Float(47, 47, 6, 6);
Ellipse2D centerPin = new Ellipse2D.Float(49, 49, 2, 2);
GeneralPath tick = new GeneralPath();

void initTick() { // tick=2*6 rectangle
    tick.moveTo(49, 0);
    tick.lineTo(51, 0);
    tick.lineTo(51, 6);
    tick.lineTo(49, 6);
    tick.lineTo(49, 0);
}

GeneralPath hourHand = new GeneralPath();

void initHourHand() {
    hourHand.moveTo(50, 15);
    hourHand.lineTo(53, 50);
    hourHand.lineTo(50, 53);
    hourHand.lineTo(47, 50);
    hourHand.lineTo(50, 15);
}

GeneralPath minuteHand = new GeneralPath();

void initMinuteHand() {
    minuteHand.moveTo(50, 2);
    minuteHand.lineTo(53, 50);
    minuteHand.lineTo(50, 58);
    minuteHand.lineTo(47, 50);
    minuteHand.lineTo(50, 2);
}

GeneralPath secondHand = new GeneralPath();

void initSecondHand() {
    secondHand.moveTo(49, 5);
    secondHand.lineTo(51, 5);
    secondHand.lineTo(51, 62);
    secondHand.lineTo(49, 62);
    secondHand.lineTo(49, 5);
}

Color faceColor = new Color(220, 220, 220);
Color hourColor = Color.red.darker();
Color minuteColor = Color.blue.darker();
Color secondColor = new Color(180, 180, 0);
Color pinColor = Color.gray.brighter();

AffineTransform hourTransform = AffineTransform.getRotateInstance(0, 50, 50);
AffineTransform minuteTransform = AffineTransform.getRotateInstance(0, 50, 50);
AffineTransform secondTransform = AffineTransform.getRotateInstance(0, 50, 50);

Timer timer = new Timer(1000, this);
Calendar calendar = Calendar.getInstance();

public Clock() {
    setPreferredSize(new Dimension(100, 100));
    initTick();
    initSecondHand();
    initMinuteHand();
    initHourHand();
}

// Invoked when panel is added to a container
public void addNotify() {
    // Call the superclass and start the timer
    super.addNotify();
    timer.start();
}

// Invoked when panel is removed from a container
public void removeNotify() {
    // Call the superclass and stop the timer
    timer.stop();
    super.removeNotify();
}

public void actionPerformed(ActionEvent event) {
    calendar.setTime(new java.util.Date());

    int hours = calendar.get(Calendar.HOUR);
    int minutes = calendar.get(Calendar.MINUTE);
    int seconds = calendar.get(Calendar.SECOND);

    hourTransform.setToRotation(((double) hours) * (Math.PI / 6.0), 50, 50);
    minuteTransform.setToRotation(((double) minutes) * (Math.PI / 30.0), 50, 50);
    secondTransform.setToRotation(((double) seconds) * (Math.PI / 30.0), 50, 50);

    repaint();
}

public void paint(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    g2.setPaint(faceColor);
    g2.fill(face);
    g2.setPaint(Color.black);
    g2.draw(face);

    for (double p = 0.0; p < 12.0; p += 1.0) {
        g2.fill(tick.createTransformedShape(AffineTransform.getRotateInstance((Math.PI / 6.0) * p, 50, 50)));
    }

    g2.setPaint(hourColor);
    g2.fill(hourHand.createTransformedShape(hourTransform));
    g2.setPaint(minuteColor);
    g2.fill(minuteHand.createTransformedShape(minuteTransform));
    g2.setPaint(secondColor);
    g2.fill(secondHand.createTransformedShape(secondTransform));
    g2.fill(pivot);
    g2.setPaint(pinColor);
    g2.fill(centerPin);
}

public static void main(String[] args) {
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().add(new Clock());
    frame.pack();
    frame.setVisible(true);
}
}
