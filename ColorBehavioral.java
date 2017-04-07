// adapted from https://www.javainterviewpoint.com/how-to-read-and-parse-csv-file-in-java/
import java.util.*;
import java.io.*;
import javax.imageio.*;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.Color;
import javax.imageio.ImageIO;

public class ColorBehavioral {

    private static final String COMMA_DELIMITER = ",";

    public static void main (String[] args) throws IOException {
        // image code
        int width = 50;
        int height = 10;

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("behinterest.csv"));

            List<Subject> subjList = new ArrayList<Subject>();

            String line = "";
            // get rid of headings
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] subjDetails = line.split(COMMA_DELIMITER);
                int id, gender;
                double picvocab, procspeed, angaffect, anghostile, fearaffect, fearsomatic, sadness, lifesatis, friendship, loneliness;

                if (subjDetails.length == 18) {
                    if (subjDetails[3].equals("M")) {
                        gender = 0;
                    } else {
                        gender = 1;
                    }
                    Subject subj = new Subject(Integer.parseInt(subjDetails[0]), gender, Double.parseDouble(subjDetails[5]), Double.parseDouble(subjDetails[6]), Double.parseDouble(subjDetails[7]), Double.parseDouble(subjDetails[8]), Double.parseDouble(subjDetails[10]), Double.parseDouble(subjDetails[11]), Double.parseDouble(subjDetails[12]), Double.parseDouble(subjDetails[13]), Double.parseDouble(subjDetails[16]), Double.parseDouble(subjDetails[17]));
                    subjList.add(subj);

                    // generate image
                    String fileName = "./pixelmaps/" + subj.id + "map.jpg";
                    BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                    int a = 0, r = 0, g = 0, b = 0, p = 0;
                    File f;
                    Color c = null;
                    if (subj.gender == 0) {
                        a = g = b = 0;
                        r = 255;
                        c = new Color(r, g, b);
                    } else if (subj.gender == 1) {
                        a = r = g = 0;
                        b = 255;
                        c = new Color(r, g, b);
                    }
                    for (int x = 0; x < width; x++) {
                        p = c.getRGB();//(a<<24) | (r<<16) | (g<<8) | b;
                        img.setRGB(x, 0, p);
                    }
                    try {
                        f = new File(fileName);
                        ImageIO.write(img, "png", f);
                    } catch(IOException e){
                        System.out.println(e);
                    }
                }

            }
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        finally {
            try {
                br.close();
            } catch (IOException ie) {
                System.out.println("Error occurred while closing buffered reader");
                ie.printStackTrace();
            }
        }
    }
}

