import java.util.*;
import java.io.*;
public class convert {
	int[] numNeighbors;
	// read in one line from file
	public LinkedList<String> readIn (File fn) {
		LinkedList<String> lines = new LinkedList<String>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(fn));
			String line = null;
			while ((line = br.readLine()) != null) {
				lines.add(line);
			}
		} catch (IOException e) {
			return null;
		}	
		finally {
			try {
				if (br != null) {
					br.close(); 
				}
			} catch (IOException ex) {
				return null;
			}
		}
//		System.out.println(lines.get(0));
		return lines;
	}
	// get dimension of matrix
	public String[] breakElements (String line) {
		String trim = line.trim();
		if (trim.isEmpty()) {
			return null;//-1;
		} 
		String[] s = trim.split("\\s+");
		return s;
	}

	public double[][] toArray (String[] line, int n) {
		double[][] arrayify = new double[n][n];
		numNeighbors = new int[n];
		int k = 0;
		for (int i = 0; i < n; i++) {
			int j = 0;
			System.out.print("[");
			while (j < n) {
				arrayify[i][j] = Double.parseDouble(line[k]);
				if (arrayify[i][j] != 0) {
					numNeighbors[i]++;
				}
				k++;
				System.out.print(arrayify[i][j] + ", ");
				j++;
			}
			System.out.println("]\n");
		}
		return arrayify;
	}	
	
	public static void main (String[] args) {
		convert testthis = new convert();
		// split data into 2d arrays for calculations 
		File f = new File("netmats2.txt");
		LinkedList<String> othertest = testthis.readIn(f);
		String lineexample = othertest.get(0);
		String[] tryingString = testthis.breakElements(lineexample);
		int tryingSize = (int) Math.sqrt(tryingString.length);
		testthis.toArray(tryingString, tryingSize);
		for (int i = 0; i < testthis.numNeighbors.length; i++) {	
			if (testthis.numNeighbors[i] != 99) {
				System.out.println("number neighbors: " + testthis.numNeighbors[0]);
			}
		}
		// calculate
		
	} 
	// n x n matrix where n = square root of number of elements in one line

	// put values into 2d array?

}
