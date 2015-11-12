package com.utilities;

import java.util.*;
import java.io.File;
import Jama.*;

import com.algorithm.hierarchicalclustering.*;

public class ClusterMap {
	File fileName = new File(
			"/home/jagvir/DataMiningHierarchicalClusteringAlgorithm/dataFiles/test.txt");
	DistanceMatrix distanceMatrixObj = new DistanceMatrix();
	Matrix distanceMatrix = distanceMatrixObj.driver(fileName);
	int distanceMatrixDimension = distanceMatrix.getColumnDimension();

	public void printMap(HashMap<Integer, Cluster> geneMap) {

		// // System.out.println(geneMap);
		for (HashMap.Entry<Integer, Cluster> ent : geneMap.entrySet()) {
			// System.out.println("here");
			System.out.print("clusterId:- " + ent.getKey() + "  geneValues:- "
					+ ent.getValue().getClusterID());

			// System.out.print("clusterId:- " + ent.getKey() +
			// "  geneValues:- ");
			// // /+ "  geneValues:- " + ent.getValue()
			// for (Gene arrayListElement : ent.getValue().getGeneList()) {
			// System.out.print(arrayListElement.getGeneValues() + " ");
			// }
			System.out.println();
		}
	}

	public void printMap(HashMap<Integer, Gene> geneMap, int x) {

		// // System.out.println(geneMap);
		for (HashMap.Entry<Integer, Gene> ent : geneMap.entrySet()) {
			// System.out.println("here");
			System.out.print("clusterId:- " + ent.getKey() + "  geneValues:- "
					+ ent.getValue());

			// System.out.print("clusterId:- " + ent.getKey() +
			// "  geneValues:- ");
			// // /+ "  geneValues:- " + ent.getValue()
			// for (Double arrayListElement : ent.getValue().getGeneValues()) {
			// System.out.print(arrayListElement + " ");
			// }
			System.out.println();
		}
	}

	private HashMap setClusterMap() {
		// System.out.println(distanceMatrixDimension);
		HashMap<Integer, Cluster> clusterMap = new HashMap<Integer, Cluster>();
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
		printMap(clusterMap);
		return clusterMap;
	}

	public static void main(String[] args) {
		ClusterMap clusterMapObj = new ClusterMap();

		HashMap<Integer, Cluster> clusterMap = clusterMapObj.setClusterMap();
		clusterMapObj.printMap(clusterMap);
	}

}
