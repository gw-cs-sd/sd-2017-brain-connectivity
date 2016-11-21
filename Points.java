import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Points extends JPanel {
  private int[] pPoints;
  private Color color;

  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D) g;

    g2d.setColor(color);

    for (int i = 0; i < pPoints.length; i++) {
      Dimension size = getSize();
      int w = size.width ;
      int h = size.height;
      int x = i % w;//x should be subject id
      int y = pPoints[i] % h; //y is correct
  //    Random r = new Random();
 //     int x = Math.abs(r.nextInt()) % w;
 //     int y = Math.abs(r.nextInt()) % h;
      g2d.drawLine(x, y, x, y);
    }
  }

  public static void run (int[] plotPoints, Color colorPoints) {
    Points points = new Points();
    JFrame frame = new JFrame("Points");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(points);
    frame.setSize(1000, 1000);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    points.pPoints = plotPoints;
    points.color = colorPoints;
  }
}
