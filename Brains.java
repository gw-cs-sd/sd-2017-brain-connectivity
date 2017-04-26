import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;

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
		ArrayList<String> varNames = new ArrayList<String>(); // to hold the column names
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
		csvData.put(0, varNames);
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
	 * This method calculates the clustering coefficient and characteristic path length of the graph
	 */
	public double smallWorldness (double[][] arrayed) {
		int n = arrayed.length;
		int[] degree = new int[n];
		int[] neighborEdges = new int[n];
		LinkedList<Integer> checkThese = new LinkedList<Integer>();
		double[] pathLength = new double[n];
		double clusCo = 0;

		int i = 0;
		while (i < n) {
			pathLength[i] = 0;
			for (int j = 0; j < n; j++) {
				// finding neighbors of node i
				if (arrayed[i][j] != 0) {
					degree[i]++; // if there is a non-zero entry here, then j is a neighbor of i
					if (!checkThese.contains(j)) {
						checkThese.add(j); // so add j to the list of nodes to check relationships between
					}
				}
				if (i != j) {
					pathLength[i] += arrayed[i][j];
				}
			}
			pathLength[i] = pathLength[i]/degree[i];

			// check number of edges between neighbors of i
			for (int k = 0; k < checkThese.size(); k++) {
				for (int m = k + 1; m < checkThese.size(); m++) {
					int check1 = checkThese.get(k);
					int check2 = checkThese.get(m);
					if (arrayed[check1][check2] != 0) {
						neighborEdges[i]++;
					}
				}
			}
			checkThese.clear();
			if (degree[i] > 1) { //catch divide by zero error if degree = 1
				clusCo += (2*neighborEdges[i])/(degree[i]*(degree[i]-1));
			}
			i++;
		}

		double charPathLength = 0;
		for (int p = 0; p < pathLength.length; p++) {
			charPathLength += pathLength[p];
		}
		charPathLength = charPathLength/pathLength.length;

		clusCo = clusCo/n; //clustering coefficient of the network is mean of clustering coefficient over all nodes

		return clusCo/charPathLength; //small-world network has large clusCo and small pathLength
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

	public double correlation (LinkedList<Integer> subjects, HashMap<Integer, ArrayList<String>> data, HashMap<Integer, Double> smallWorld, int varIndex) {
		// only look at subjects in subjects list
		// correlation coefficient definition from Wikipedia
		int n = subjects.size();
		double r = 0;
		double combSum = 0;
		double plSum = 0;
		double dSum = 0;
		double d = 0;
		double pl = 0;
		double dSquare = 0;
		double plSquare = 0;

		for (int i = 0; i < n; i++) {
			try {
				d = Double.valueOf(data.get(subjects.get(i)).get(varIndex));
			} catch (NumberFormatException e) {
				if (data.get(subjects.get(i)).get(varIndex).equals(" ")) {
					continue;
				} else {
					return 10;
				}
			} catch (IndexOutOfBoundsException e) {
				e.printStackTrace();
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

		// get network matrices
		File f = new File("netmats2.txt");
		LinkedList<String> brainCon = brain.readIn(f);
		HashMap<Integer, Double> swMeasures = new HashMap<Integer, Double>();
		Points points = new Points();

		for (int i = 0; i < subjects1.size(); i++) {
			int currentSubject = subjects1.get(i);

            		// split data into 2d arrays for calculations
			String[] netmats = brain.breakElements(brainCon.get(i));
			int netmatSize = (int) Math.sqrt(netmats.length);
			double[][] netmatArr = brain.toArray(netmats, netmatSize);

			int[] indivPoints = new int[netmats.length];
			for (int j = 0; j < indivPoints.length; j++) {
				indivPoints[j] = (int) (Double.valueOf(netmats[j]) * 1000);
			}

			// plot each brain
			points.run(currentSubject, indivPoints, Color.BLACK);

			// calculate small-worldness for each subject
			swMeasures.put(currentSubject, brain.smallWorldness(netmatArr));
		}

		// get csv file
		File csvFile = new File("unrestricted_kvwalker_10_25_2016_19_32_30.csv");
		HashMap<Integer, ArrayList<String>> behData = brain.readCsv(csvFile);
		LinkedList<Integer> subjects = brain.trimSubjects(subjects1, behData);

		// compare to CSV of behaviors
		try (Writer br = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("correlations.txt"), "utf-8"))) {
			int numVars = behData.get(subjects.get(0)).size();
			double[] corrs = new double[numVars];
			for (int i = 0; i < numVars; i++) {
				corrs[i] = brain.correlation(subjects, behData, swMeasures, i);
				if (Math.abs(corrs[i]) > 0.01 && Math.abs(corrs[i]) != Double.POSITIVE_INFINITY && corrs[i] != 10) {
					try {
						if (corrs[i] < 0) {
							br.write("r: " + Double.toString(corrs[i]) + ", Variable: " + behData.get(0).get(i) + "\n");
						} else {
							br.write("r:  " + Double.toString(corrs[i]) + ", Variable: " + behData.get(0).get(i) + "\n");
						}
					} catch(IOException e) {}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
