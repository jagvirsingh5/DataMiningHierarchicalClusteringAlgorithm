package com.utilities;

import java.util.*;
import java.io.File;
import Jama.*;

import com.algorithm.hierarchicalclustering.*;

public class ClusterMap {
	File fileName = new File(
			"/home/jagvir/DataMiningHierarchicalClusteringAlgorithm/dataFiles/test.txt");
	// File fileName = new File(
	// "/home/jagvir/DataMiningHierarchicalClusteringAlgorithm/dataFiles/iyer.txt");
	DistanceMatrix distanceMatrixObj = new DistanceMatrix();
	Matrix distanceMatrix = distanceMatrixObj.driver(fileName);
	int distanceMatrixDimension = distanceMatrix.getColumnDimension();
	int counter = 0;

	public void printMap(HashMap<Integer, Cluster> clusterMap) {

		// // System.out.println(geneMap);
		for (HashMap.Entry<Integer, Cluster> ent : clusterMap.entrySet()) {
			// System.out.println("here");
			// System.out.print("clusterId:- " + ent.getKey() +
			// "  geneValues:- "
			// + ent.getValue().getClusterID());

			System.out.print("clusterId:- " + ent.getKey() + "  geneValues:- ");
			// + ent.getValue().getGeneList());
			// /+ "  geneValues:- " + ent.getValue()
			for (Gene arrayListElement : ent.getValue().getGeneList()) {
				System.out.print(arrayListElement.getGeneId() + " ");
			}
			System.out.println();
		}
	}

	public void printMap(HashMap<Integer, Gene> geneMap, int x) {

		// // System.out.println(geneMap);
		for (HashMap.Entry<Integer, Gene> ent : geneMap.entrySet()) {
			// System.out.println("here");
			System.out.println("clusterId:- " + ent.getKey()
					+ "  geneValues:- " + ent.getValue());

			// System.out.print("clusterId:- " + ent.getKey() +
			// "  geneValues:- ");
			// // /+ "  geneValues:- " + ent.getValue()
			// for (Double arrayListElement : ent.getValue().getGeneValues()) {
			// System.out.print(arrayListElement + " ");
			// }
			// System.out.println();
		}
	}

	HashMap<Integer, Cluster> clusterMap = new HashMap<Integer, Cluster>();

	private HashMap ClusterMapInit() {
		// System.out.println(distanceMatrixDimension);

		HashMap<Integer, Gene> geneMap = distanceMatrixObj.FileReader(fileName);
		for (HashMap.Entry<Integer, Gene> ent : geneMap.entrySet()) {
			int key = ent.getKey();
			Gene value = ent.getValue();
			// System.out.println(value.getGeneId());
			ArrayList<Gene> geneList = new ArrayList<Gene>();
			geneList.add(value);
			Cluster clusterObj = new Cluster();
			clusterObj.setGeneList(geneList);
			clusterObj.setClusterID(key);
			clusterMap.put(key, clusterObj);
		}
		// printMap(clusterMap);
		return clusterMap;
	}

	private void setClusterMap() {
		// distanceMatrixObj.printMatrix(distanceMatrix);
		// ClusterMapInit();
		int i = 0;
		int clusterCount = clusterMap.size() * clusterMap.size();
		// System.out.println(clusterCount);
		while (i < 9) {
			setSmallestDistance();
			// clusterCount--;
			i++;
		}

		printMap(clusterMap);

		//
	}

	private void setSmallestDistance() {

		// System.out.println(Double.MAX_VALUE);

		int smallestI = 0;
		int smallestJ = 0;
		int length = distanceMatrix.getColumnDimension();
		// System.out.println();
		double smallestElement = 100000.0;
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {
				// System.out.println(distanceMatrix.get(i, j));
				if (smallestElement >= distanceMatrix.get(i, j)
						&& distanceMatrix.get(i, j) != 0.0
						&& !isGenePairPresent(i, j)
						&& distanceMatrix.get(i, j) < 100000.0) {
					// System.out.println("Herre");
					smallestElement = distanceMatrix.get(i, j);

					smallestI = i;
					smallestJ = j;

					//

				}
			}
		}
		distanceMatrix.set(smallestI, smallestJ, 100000.0);
		distanceMatrix.set(smallestJ, smallestI, 100000.0);
		// System.out.println("jere");
		// distanceMatrixObj.printMatrix(distanceMatrix);

		mergeCluster(smallestI, smallestJ, smallestElement);
		// printMap(clusterMap);

	}

	private void mergeCluster(int i, int j, double smallestElement) {
		// if (smallestElement < 1000.0) {
		// System.out.println("i = " + i + " j = " + j
		// + "         :- smallestElement " + smallestElement);
		// }

		int geneCounter = 0;
		ArrayList<Gene> tempList = new ArrayList<Gene>();
		int keyToAdd = 0;
		int keyToRemove = 0;
		for (HashMap.Entry<Integer, Cluster> ent : clusterMap.entrySet()) {
			// System.out.println(i + " " + j);
			ArrayList<Gene> geneList = ent.getValue().getGeneList();

			for (int k = 0; k < geneList.size(); k++) {
				if (i == geneList.get(k).getGeneId()) {
					System.out.println(i + " = to "
							+ geneList.get(k).getGeneId());
					geneCounter++;
				}
				if (j == geneList.get(k).getGeneId()) {
					System.out.println(j + " = to "
							+ geneList.get(k).getGeneId());
					geneCounter++;
				}

			}
			if (geneCounter == 2) {

				System.out.println("here " + i + " " + j);
				geneCounter = 0;
				return;
			}

			for (int k = 0; k < geneList.size(); k++) {
				if (i == geneList.get(k).getGeneId()) {
					tempList.addAll(geneList);
					keyToAdd = ent.getKey();
				}
				if (j == geneList.get(k).getGeneId()) {
					tempList.addAll(geneList);
					keyToRemove = ent.getKey();
				}
			}

		}

		for (int m = 0; m < tempList.size(); m++) {
			System.out.print(tempList.get(m).getGeneId() + " ");
		}
		System.out.println();
		// clusterMap.remove(keyToAdd);

		Cluster clusterObj = new Cluster();
		clusterObj.setGeneList(tempList);
		clusterObj.setClusterID(keyToAdd);
		clusterMap.put(keyToAdd, clusterObj);
		clusterMap.remove(keyToRemove);

	}

	private boolean isGenePairPresent(int i, int j) {
		int counter = 0;
		for (HashMap.Entry<Integer, Cluster> ent : clusterMap.entrySet()) {
			ArrayList<Gene> geneList = ent.getValue().getGeneList();
			for (int k = 0; k < geneList.size(); k++) {

				if (i == geneList.get(k).getGeneId()) {
					counter++;
					// System.out.print(i + " " + geneList.get(k).getGeneId()
					// + " ");
				}
				if (j == geneList.get(k).getGeneId()) {
					counter++;
					// System.out.print(j + " " + geneList.get(k).getGeneId()
					// + " ");
				}
			}
			counter = 0;
			// System.out.println();
			if (counter == 2) {
				return true;
			}

		}
		return false;
	}

	public static void main(String[] args) {
		ClusterMap clusterMapObj = new ClusterMap();
		System.out.println();
		HashMap<Integer, Cluster> clusterMap = clusterMapObj.ClusterMapInit();
		// clusterMapObj.printMap(clusterMap);
		clusterMapObj.setClusterMap();

	}
}
