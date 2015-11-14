package com.hierarchicalClustering;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

public class Main {

	static HashMap<Integer, ArrayList<Integer>> groundTruth = new HashMap<Integer, ArrayList<Integer>>();
	static HashMap<Integer, Integer> gtMap = new HashMap<Integer, Integer>();
	static HashMap<Integer, ArrayList<Double>> geneMap = new HashMap<Integer, ArrayList<Double>>();
	static HashMap<Integer, Integer> clusterMap = new HashMap<Integer, Integer>();
	static ArrayList<Integer> corePoints = new ArrayList<Integer>();
	static HashMap<Double, ArrayList<ArrayList<Integer>>> distMap = new HashMap<Double, ArrayList<ArrayList<Integer>>>();
	static ArrayList<HashSet<Integer>> allClusters = new ArrayList<HashSet<Integer>>();
	static HashMap<Integer, ArrayList<Integer>> clusters = new HashMap<Integer, ArrayList<Integer>>();
	static ArrayList<HashMap<Integer, ArrayList<Double>>> listOfMaps = new ArrayList<HashMap<Integer, ArrayList<Double>>>();
	static int sampleSize;
	static double distArr[][];
	static double distMat[][];
	static boolean isProcessed[];
	static int k = 0;
	static ArrayList<Double> distList = new ArrayList<Double>();
	static HashMap<Integer, Integer> m1 = new HashMap<Integer, Integer>();

	public static void main(String[] args) throws NumberFormatException,
			IOException {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);

		System.out.println("Enter value of K:");
		k = scan.nextInt();
		FileReader frd = new FileReader(
				"/home/jagvir/DataMiningHierarchicalClusteringAlgorithm/dataFiles/iyer.txt");
		BufferedReader bfr = new BufferedReader(frd);
		readFile(bfr);
		createClusters();
		scan.close();
	}

	public static void createClusters() throws IOException {
		int i = 0, j = 0;
		int minI = -1, minJ = -1;
		double minDistance = Double.MAX_VALUE;

		while (listOfMaps.size() > k) {
			i = 0;
			j = 0;
			minI = -1;
			minJ = -1;
			minDistance = Double.MAX_VALUE;
			for (i = 0; i < listOfMaps.size() - 1; i++) {
				for (j = i + 1; j < listOfMaps.size(); j++) {
					Set<Integer> p1Keys = listOfMaps.get(i).keySet();
					Set<Integer> p2Keys = listOfMaps.get(j).keySet();
					ArrayList<Integer> p1KeysList = new ArrayList<Integer>();
					ArrayList<Integer> p2KeysList = new ArrayList<Integer>();
					Iterator<Integer> iter1 = p1Keys.iterator();
					Iterator<Integer> iter2 = p2Keys.iterator();

					HashMap<Integer, ArrayList<Double>> mapAtI = listOfMaps
							.get(i);
					HashMap<Integer, ArrayList<Double>> mapAtJ = listOfMaps
							.get(j);

					// System.out.println(mapAtI);

					while (iter1.hasNext())
						p1KeysList.add(iter1.next());
					while (iter2.hasNext())
						p2KeysList.add(iter2.next());

					for (int p = 0; p < p1KeysList.size(); p++) {
						for (int q = 0; q < p2KeysList.size(); q++) {
							if (getEuclideanDistance(
									mapAtI.get(p1KeysList.get(p)),
									mapAtJ.get(p2KeysList.get(q))) < minDistance) {
								minDistance = getEuclideanDistance(
										mapAtI.get(p1KeysList.get(p)),
										mapAtJ.get(p2KeysList.get(q)));
								minI = i;
								minJ = j;
							}
						}
					}
				}
			}

			HashMap<Integer, ArrayList<Double>> mapAtI = listOfMaps.get(minI);
			HashMap<Integer, ArrayList<Double>> mapAtJ = listOfMaps.get(minJ);
			for (Map.Entry<Integer, ArrayList<Double>> ent : mapAtJ.entrySet()) {
				mapAtI.put(ent.getKey(), ent.getValue());
			}
			listOfMaps.remove(minJ);
		}

		System.out.println(listOfMaps);

		// calculating clusterMap
		int clusterIndex = 1;
		FileWriter fwr = new FileWriter(new File("Gene-ClusterId-Map-HCA.txt"));
		for (HashMap<Integer, ArrayList<Double>> eachMap : listOfMaps) {
			for (Map.Entry<Integer, ArrayList<Double>> ent : eachMap.entrySet()) {
				clusterMap.put(ent.getKey(), clusterIndex);
				fwr.write(ent.getKey() + "," + clusterIndex);
				fwr.write("\n");
			}
			clusterIndex++;
		}

		fwr.close();

		// creating map of Clusters
		clusterIndex = 1;
		for (HashMap<Integer, ArrayList<Double>> eachMap : listOfMaps) {
			ArrayList<Integer> cluserList = new ArrayList<Integer>();
			for (Map.Entry<Integer, ArrayList<Double>> ent : eachMap.entrySet()) {
				cluserList.add(ent.getKey());
			}
			clusters.put(clusterIndex, cluserList);
			clusterIndex++;
		}

		TreeMap<Integer, Integer> gtTreeMap = new TreeMap<Integer, Integer>(
				gtMap);
		Set<Integer> gtKeys = gtTreeMap.keySet();
		ArrayList<Integer> gtKeysList = new ArrayList<Integer>();
		Iterator<Integer> gtIter = gtKeys.iterator();
		while (gtIter.hasNext())
			gtKeysList.add(gtIter.next());

		System.out
				.println("Jaccard coeff:" + calculateJaccardCoeff(gtKeysList));
		silhoutteCalculate();

	}

	public static double calculateJaccardCoeff(ArrayList<Integer> gtKeysList) {
		int m11 = 0;
		int m10 = 0, m01 = 0;

		int i, j;
		for (i = 0; i < gtKeysList.size() - 1; i++) {
			for (j = i + 1; j < gtKeysList.size(); j++) {
				int p1 = gtKeysList.get(i);
				int p2 = gtKeysList.get(j);
				if (gtMap.get(p1) == gtMap.get(p2)
						&& clusterMap.get(p1) == clusterMap.get(p2))
					m11++;
				else if (gtMap.get(p1) == gtMap.get(p2)
						&& clusterMap.get(p1) != clusterMap.get(p2))
					m10++;
				else if (gtMap.get(p1) != gtMap.get(p2)
						&& clusterMap.get(p1) == clusterMap.get(p2))
					m01++;

			}
		}

		return ((double) m11 / (double) (m11 + m01 + m10));
	}

	public static void silhoutteCalculate() {
		Set<Integer> clusterIdkeys = clusters.keySet();
		ArrayList<Integer> clusterIdKeysList = new ArrayList<Integer>();
		Iterator<Integer> iterator = clusterIdkeys.iterator();
		while (iterator.hasNext()) {
			clusterIdKeysList.add(iterator.next());
		}

		// calculate a[i]
		double ai = 0;
		int clusterId = clusterIdKeysList.get(0);
		ArrayList<Integer> geneList = clusters.get(clusterId);
		int chosenPoint = geneList.get(0);
		for (int point : geneList) {
			if (point == chosenPoint)
				;
			else {
				ai = ai + distMat[chosenPoint][point];
			}
		}
		ai = ai / (double) (geneList.size() - 1);
		ArrayList<Double> biList = new ArrayList<Double>();
		if (clusters.size() == 1)
			biList.add(ai);
		else {
			for (Map.Entry<Integer, ArrayList<Integer>> ent : clusters
					.entrySet()) {
				double bi = 0;
				if (ent.getKey() == clusterId)
					;
				else {
					ArrayList<Integer> list = ent.getValue();
					for (int elem : list) {
						bi = bi + distMat[chosenPoint][elem];
					}
					biList.add(bi);
				}
			}
		}

		Collections.sort(biList);
		double silhoutte = 0;
		silhoutte = Math.abs((biList.get(0) - ai)
				/ (Math.max(biList.get(0), ai)));
		System.out.println("silhoutte index is:" + silhoutte);

	}

	public static void readFile(BufferedReader bfr)
			throws NumberFormatException, IOException {
		String line = "";
		int initialClustId = 1;
		while ((line = bfr.readLine()) != null) {

			String lSplit[] = line.split("\t");
			if (lSplit.length == 2) {
				sampleSize = Integer.parseInt(lSplit[0]) + 1;
				isProcessed = new boolean[sampleSize];
				continue;
			}
			int geneId = Integer.parseInt(line.split("\t")[0]);
			m1.put(geneId, geneId);
			int groundClId = Integer.parseInt(line.split("\t")[1]);
			ArrayList<Double> list = new ArrayList<Double>();
			int i = 0;
			for (int j = 2; j < lSplit.length; j++) {
				list.add(i, Double.parseDouble(lSplit[j]));
				i++;
			}

			// -------------------constructing
			// listOfMaps--------------------------
			HashMap<Integer, ArrayList<Double>> geneExpressions = new HashMap<Integer, ArrayList<Double>>();
			geneExpressions.put(geneId, list);
			listOfMaps.add(geneExpressions);
			// -------------------END constructing
			// listOfMaps--------------------------

			ArrayList<Integer> individualGeneList = new ArrayList<Integer>();
			individualGeneList.add(geneId);
			clusters.put(initialClustId, individualGeneList);
			initialClustId++;

			if (!gtMap.containsKey(geneId)) {
				gtMap.put(geneId, groundClId);
			}

			if (!groundTruth.containsKey(groundClId)) {
				ArrayList<Integer> geneList = new ArrayList<Integer>();
				geneList.add(geneId);
				groundTruth.put(groundClId, geneList);
			} else {
				groundTruth.get(groundClId).add(geneId);
			}
			if (!geneMap.containsKey(geneId)) {
				geneMap.put(geneId, list);
			}
		}

		// distance matrix generation
		distArr = new double[geneMap.size() + 1][geneMap.size() + 1];
		distMat = new double[geneMap.size() + 1][geneMap.size() + 1];
		for (int i = 1; i <= geneMap.size(); i++) {
			for (int j = 1; j < i; j++) {
				distArr[i][j] = getEuclideanDistance(geneMap.get(i),
						geneMap.get(j));
				distArr[j][i] = getEuclideanDistance(geneMap.get(i),
						geneMap.get(j));
			}
		}
		distMat = distArr;

	}

	public static double getEuclideanDistance(ArrayList<Double> arr1,
			ArrayList<Double> arr2) {
		double distance = 0.0;

		// System.out.println(arr1.size()+", "+arr2.size());
		for (int i = 0; i < arr1.size(); i++) {
			distance = distance
					+ ((arr1.get(i) - arr2.get(i)) * (arr1.get(i) - arr2.get(i)));
		}
		distance = Math.sqrt(distance);

		return distance;
	}

}