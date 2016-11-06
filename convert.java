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

	// put values into 2d array for easier access
	public double[][] toArray (String[] line, int n) {
		double[][] arrayify = new double[n][n];
		numNeighbors = new int[n];
		int k = 0;
		for (int i = 0; i < n; i++) {
			int j = 0;
			while (j < n) {
				arrayify[i][j] = Double.parseDouble(line[k]);
				k++;
				j++;
			}
		}
		return arrayify;
	}	
	
	public double correlationCoefficient (double[][] arrayed) {
		int n = arrayed.length;
		int[] neighbors = new int[n];
		int[] neighborEdges = new int[n];
		LinkedList<Integer> checkThese = new LinkedList<Integer>();
		double corrCo = 0;
		double pathLength = 0;

		int i = 0;
		while (i < n) {
			for (int j = 0; j < n; j++) {
				// finding neighbors of node i
				if (arrayed[i][j] != 0) {
					neighbors[i]++; // if there is a non-zero entry here, then j is a neighbor of i
					if (!checkThese.contains(j)) {
						checkThese.add(j); // so add j to the list of nodes to check relationships between
					}
				}
				if (i != j) {
					pathLength += 1/(arrayed[i][j]);
				}
				// check relationships between neighbors of i here
			}
			for (int k = 0; k < checkThese.size(); k++) {
				for (int m = k + 1; m < checkThese.size(); m++) {
					if (arrayed[k][m] != 0) {
						neighborEdges[i]++;
					}
				}
			}
			checkThese.clear();
			if (neighbors[i] > 1) {
				corrCo += (2*neighborEdges[i])/(neighbors[i]*(neighbors[i]-1));
			}
			i++;
		}
		corrCo = corrCo/n; 
		pathLength = pathLength/(n*(n-1));
		return pathLength;
	}

	public LinkedList<Integer> readCsv (File fCSV) {

	}

	public LinkedList<Integer> trimSubjects (LinkedList<Integer> l1, LinkedList<Integer> l2) {
		// return elements in common
		LinkedList<Integer> inCommon = new LinkedList<Integer>();

		for (int i = 0; i < l1.size(); i++) {
			if (l2.contains(l1.get(i))) {
				inCommon.add(l1.get(i));
			}
		}

		return inCommon;
	}
	
	public static void main (String[] args) {
		convert testthis = new convert();
		// split data into 2d arrays for calculations 
		File f = new File("netmats2.txt");
		LinkedList<String> othertest = testthis.readIn(f);
		for (int i = 0; i < othertest.size(); i++) {
			String[] tryingString = testthis.breakElements(othertest.get(i));
			int tryingSize = (int) Math.sqrt(tryingString.length);
			double[][] testArrayify = testthis.toArray(tryingString, tryingSize);

			// calculate correlation coefficient
			double hopethisworks = testthis.correlationCoefficient(testArrayify);
		}

		// get subject IDs
		File subjFile = new File("subjectIDs.txt");
		LinkedList<String> subj1 = testthis.readIn(subjFile);
		LinkedList<Integer> subjects1 = new LinkedList<Integer>();
		for (int i = 0; i < subj1.size(); i++) {
			String[] tryStr = testthis.breakElements(subj1.get(i));
			subjects.add(Integer.valueOf(tryStr[0]));
			System.out.println(subjects.get(i));
		}

		// get csv file
		File csvFile = new File("blah.csv");
		csvify = testthis.readCsv(f);
		testthis.trimSubjects(subjects1, csvify);

		// compare to CSV of behaviors
		compareCorr(csvify, hopethisworks); // need to remove mismatched subject ids	
	
	}
	// n x n matrix where n = square root of number of elements in one line

	// put values into 2d array?

}
