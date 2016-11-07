import java.util.*;
import java.io.*;

public class Brains {
	int[] numNeighbors;
	
	/* 
	 * These methods process files
	 */
	public LinkedList<String> readIn (File fn) {
		LinkedList<String> lines = new LinkedList<String>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(fn));
			String line = null;
			while ((line = br.readLine()) != null) {
				lines.add(line);
			}
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
		return lines;
	}
	
	public HashMap<Integer, ArrayList<String>> readCsv (File fCSV) {
		LinkedList<String> varNames = new LinkedList<String>(); // to hold the column names
		HashMap<Integer, ArrayList<String>> csvData = new HashMap<Integer, ArrayList<String>>(); //HashMap with subject ID as key and ArrayList of variables as value
		BufferedReader br = null;
		String splitBy = ",";
		
		try {
			br = new BufferedReader(new FileReader(fCSV));
			String line = null;
			while ((line = br.readLine()) != null) {
				if (line.contains("Subject")) {
					String[] variables = line.split(splitBy);
					for (int i = 0; i < variables.length; i++) {
						varNames.add(variables[i]);
					}
				} else {
					String[] splitLine = line.split(splitBy);
					int id = Integer.valueOf(splitLine[0]);
					csvData.put(id, new ArrayList<String>());
					for (int i = 1; i < splitLine.length; i++) {
						csvData.get(id).add(splitLine[i]);
					}
				}		
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		}
		return csvData;	
	}

	/*
	 * These methods create a 2d matrix
	 */
	public String[] breakElements (String line) {// get dimension of matrix
		String trimLine = line.trim();
		if (trimLine.isEmpty()) {
			return null;
		}
		String[] s = trimLine.split("\\s+");
		return s;
	}

	public double[][] toArray (String[] line, int n) {// put values into 2d array for easier access
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

	/*
	 * This method calculates the clustering coefficient and characteristic path length of each node in the graph
	 */	
	public double clusteringCoefficient (double[][] arrayed) {
		int n = arrayed.length;
		int[] neighbors = new int[n];
		int[] neighborEdges = new int[n];
		LinkedList<Integer> checkThese = new LinkedList<Integer>();
		double clusCo = 0;
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
				clusCo += (2*neighborEdges[i])/(neighbors[i]*(neighbors[i]-1));
			}
			i++;
		}
		clusCo = clusCo/n; 
		pathLength = pathLength/(n*(n-1));
		return pathLength; //return pathLength because the clustering coefficient is the same for every node in this dataset
	}

	/*
	 * This method returns the subject IDs that have both brain and behavioral data
	 */
	public LinkedList<Integer> trimSubjects (LinkedList<Integer> l1, HashMap<Integer, ArrayList<String>> l2) {
		// return elements in common
		LinkedList<Integer> inCommon = new LinkedList<Integer>();

		for (int i = 0; i < l1.size(); i++) {
			if (l2.containsKey(l1.get(i))) {
				inCommon.add(l1.get(i));				
			}
		}

		return inCommon;
	}

	public double correlation (LinkedList<Integer> subjects, HashMap<Integer, ArrayList<String>> data, HashMap<Integer, Double> smallWorld) {
		// only look at subjects in subjects list
		int n = subjects.size();
		double r = 0;

		for (int j = 0; j < 546; j++) {
			double combSum = 0;
			double plSum = 0;
			double dSum = 0;	
			r = 0;
			double d = 0;
			double pl = 0;
			double dSquare = 0;
			double plSquare = 0;
			
			for (int i = 0; i < n; i++) {
				try { 
					d = Double.valueOf(data.get(subjects.get(i)).get(j));
				} catch (NumberFormatException e) {
					System.out.print("Not number");
					r = -10;
					break;
				}
				dSum += d;
				dSquare += Math.pow(d, 2);

				pl = smallWorld.get(subjects.get(i));
				plSum += pl;
				plSquare += Math.pow(pl, 2);

				combSum += d*pl;
			}
			combSum *= n;
			double dDiff = Math.sqrt(n*dSquare - Math.pow(dSum, 2));
			double plDiff = Math.sqrt(n*plSquare - Math.pow(plSum, 2));
			r = (combSum - dSum*plSum)/(dDiff*plDiff);
			System.out.println("r: " + r);
		}
		return r;	
	}
	
	public static void main (String[] args) {
		Brains brain = new Brains();

		// get subject IDs
		File subjFile = new File("subjectIDs.txt");
		LinkedList<String> subj1 = brain.readIn(subjFile);
		LinkedList<Integer> subjects1 = new LinkedList<Integer>();
		for (int i = 0; i < subj1.size(); i++) {
			subjects1.add(Integer.valueOf(subj1.get(i)));
		}

		// split data into 2d arrays for calculations 
		File f = new File("netmats2.txt");
		LinkedList<String> brainCon = brain.readIn(f);
		HashMap<Integer, Double> pLengths = new HashMap<Integer, Double>();

		for (int i = 0; i < brainCon.size(); i++) {
			String[] netmats = brain.breakElements(brainCon.get(i));
			int netmatSize = (int) Math.sqrt(netmats.length);
			double[][] netmatArr = brain.toArray(netmats, netmatSize);

			// calculate correlation coefficient
			pLengths.put(subjects1.get(i), brain.clusteringCoefficient(netmatArr));
		}

		// get csv file
		File csvFile = new File("behavioral.csv");
		HashMap<Integer, ArrayList<String>> behData = brain.readCsv(csvFile);
		LinkedList<Integer> subjects = brain.trimSubjects(subjects1, behData);
		
		// compare to CSV of behaviors
		double corrs = brain.correlation(subjects, behData, pLengths);
	}
	// n x n matrix where n = square root of number of elements in one line

	// put values into 2d array?

}
