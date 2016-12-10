import java.util.*;
import java.io.*;

public class New {
        public static void main (String[] args) {
        File f = new File("netmats.txt");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(f));
            Writer bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("matrix.txt"), "utf-8"));
            String line = null;
            int i = 0;
            int j = 0;
            while ((line = br.readLine()) != null) {
//                line = line.trim();
                String[] s = line.split("\\s+");
                int k = 0;
                while (k < s.length) {
                    while (j < 100) {
                        bw.write(i + " " + j + " " + s[k] + "\n");
                        k++;
                        j++;
                    }
                    j = 0;
                    i++;
                }
            }
            bw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();;
            }
        }
    }
}

