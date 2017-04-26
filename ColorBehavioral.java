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
        int width = 300;
        int height = 20;

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
                    int p = 0;
                    // set rgb values to the website color scheme values
                    int r = 240;
                    int g = 140;
                    int b = 138;
                    // alpha to adjust shade of color
                    int a = 0;
                    // color
                    Color c = null;
                    // if male, blue. if female, pink
                    if (subj.gender == 0) {
                        c = new Color(132, 240, 240);
                    } else if (subj.gender == 1) {
                        a = 255;
                        c = new Color(r, g, b);
                    }
                    p = c.getRGB();
                    for (int x = 0; x < (int) width/11; x++) {
                        for (int y = 0; y < height; y++) {
                            img.setRGB(x, y, p);
                        }
                    }
                    // picvocab
                    a = (int) ((subj.picvocab - 255)*(subj.picvocab - 255));
                    c = new Color(189, 242, 133, a%255);
                    p = c.getRGB();
                    for (int x = (int) width/11; x < (int) 2*width/11; x++) {
                        for (int y = 0; y < height; y++) {
                            img.setRGB(x, y, p);
                        }
                    }
                    // procspeed - pink
                    r = 242; g = 141; b = 148;
                    a = (int) ((subj.procspeed - 255)*(subj.procspeed - 255));
                    c = new Color(r, g, b, a%255);
                    p = c.getRGB();
                    for (int x = (int) 2*width/11; x < (int) 3*width/11; x++) {
                        for (int y = 0; y < height; y++) {
                            img.setRGB(x, y, p);
                        }
                    }
                    // angaffect - orange
                    r = 214; g = 117; b = 81;
                    a = (int) ((subj.angaffect - 255)*(subj.angaffect - 255));
                    c = new Color(r, g, b, a%255);
                    p = c.getRGB();
                    for (int x = (int) 3*width/11; x < (int) 4*width/11; x++) {
                        for (int y = 0; y < height; y++) {
                            img.setRGB(x, y, p);
                        }
                    }
                    // anghostile - red
                    r = 214; g = 83; b = 81;
                    a = (int) ((subj.anghostile - 255)*(subj.anghostile - 255));
                    c = new Color(r, g, b, a%255);
                    p = c.getRGB();
                    for (int x = (int) 4*width/11; x < (int) 5*width/11; x++) {
                        for (int y = 0; y < height; y++) {
                            img.setRGB(x, y, p);
                        }
                    }
                    // fearaffect - purple
                    r = 185; g = 82; b = 245;
                    a = (int) ((subj.fearaffect - 255)*(subj.fearaffect - 255));
                    c = new Color(r, g, b, a%255);
                    p = c.getRGB();
                    for (int x = (int) 5*width/11; x < (int) 6*width/11; x++) {
                        for (int y = 0; y < height; y++) {
                            img.setRGB(x, y, p);
                        }
                    }
                    // fearsomatic - lilac
                    r = 157; g = 107; b = 245;
                    a = (int) ((subj.fearsomatic - 255)*(subj.fearsomatic - 255));
                    c = new Color(r, g, b, a%255);
                    p = c.getRGB();
                    for (int x = (int) 6*width/11; x < (int) 7*width/11; x++) {
                        for (int y = 0; y < height; y++) {
                            img.setRGB(x, y, p);
                        }
                    }
                    // sadness - blue
                    r = 57; g = 126; b = 217;
                    a = (int) ((subj.sadness - 255)*(subj.sadness - 255));
                    c = new Color(r, g, b, a%255);
                    p = c.getRGB();
                    for (int x = (int) 7*width/11; x < (int) 8*width/11; x++) {
                        for (int y = 0; y < height; y++) {
                            img.setRGB(x, y, p);
                        }
                    }
                    // lifesatis - green
                    r = 138; g = 240; b = 147;
                    a = (int) ((subj.lifesatis - 255)*(subj.lifesatis - 255));
                    c = new Color(r, g, b, a%255);
                    p = c.getRGB();
                    for (int x = (int) 8*width/11; x < (int) 9*width/11; x++) {
                        for (int y = 0; y < height; y++) {
                            img.setRGB(x, y, p);
                        }
                    }
                    // friendship - yellow
                    r = 240; g = 228; b = 135;
                    a = (int) ((subj.friendship - 255)*(subj.friendship - 255));
                    c = new Color(r, g, b, a%255);
                    p = c.getRGB();
                    for (int x = (int) 9*width/11; x < (int) 10*width/11; x++) {
                        for (int y = 0; y < height; y++) {
                            img.setRGB(x, y, p);
                        }
                    }
                    // loneliness - grey
                    r = 156; g = 157; b = 158;
                    a = (int) ((subj.loneliness - 255)*(subj.loneliness - 255));
                    c = new Color(r, g, b, a%255);
                    p = c.getRGB();
                    for (int x = (int) 10*width/11; x < width; x++) {
                        for (int y = 0; y < height; y++) {
                            img.setRGB(x, y, p);
                        }
                    }

                    // write to file
                    File f;
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

