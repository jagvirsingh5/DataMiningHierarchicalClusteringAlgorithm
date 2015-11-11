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

	private void setClusterMap() {
		System.out.println(distanceMatrixDimension);
		HashMap<Integer, Cluster> clusterMap = new HashMap<Integer, Cluster>();
		for (int i = 0; i < distanceMatrixDimension; i++) {
		}
	}

	public static void main(String[] args) {
		ClusterMap clusterMap = new ClusterMap();
		clusterMap.setClusterMap();

	}

}
