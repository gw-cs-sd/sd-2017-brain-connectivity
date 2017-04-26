import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.*;
import java.io.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import java.awt.image.*;

public class Points extends JPanel {
  private int[] subjects;
  private int[] pPoints;
  private Color color;
  private int k;

  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    File f = new File("./images/image" + k + ".JPG");

    try {
        BufferedImage bi = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bi.createGraphics();

        g2d.setColor(color);

        for (int i = 0; i < pPoints.length; i++) {
            Dimension size = getSize();
            int w = size.width ;
            int h = size.height;
            int x = i % w;
            int y = pPoints[i] % h;
            g2d.drawLine(x, y, x, y);
        }

            ImageIO.write(bi, "PNG", f);

    } catch (IOException ie) {
        ie.printStackTrace();
    }

  }

  public static void run (int subjID, int[] plotPoints, Color colorPoints) {
    Points points = new Points();
    int width = 1000;
    int height = 1000;

    JFrame frame = new JFrame("Network Matrix for Subject " + subjID);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(points);
    frame.setSize(1000, 1000);
    frame.setVisible(true);

    points.pPoints = plotPoints;
    points.color = colorPoints;
    points.k = subjID;
 }
}
