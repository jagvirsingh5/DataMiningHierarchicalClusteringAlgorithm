package com.algorithm.hierarchicalclustering;

import java.util.*;
import java.util.Map.Entry;
import java.io.*;
import Jama.*;

import com.utilities.Gene;

public class DistanceMatrix {

	/**
	 * @param fileName
	 *            where fileName is the location of the file in the folder
	 */
	static int geneCount = 0;
	static int attributeCount = 0;

	private void printMap(HashMap<Integer, Gene> geneMap) {
		System.out.println(geneCount + " " + attributeCount);
		// // System.out.println(geneMap);
		for (HashMap.Entry<Integer, Gene> ent : geneMap.entrySet()) {
			// System.out.println("here");
			System.out.print("clusterId:- " + ent.getKey() + "  geneValues:- ");

			// System.out.print("clusterId:- " + ent.getKey() +
			// "  geneValues:- ");
			// // /+ "  geneValues:- " + ent.getValue()
			for (Double arrayListElement : ent.getValue().getGeneValues()) {
				System.out.print(arrayListElement + " ");
			}
			System.out.println();
		}
	}

	private void printMatrix(double[][] matrixArr) {
		for (int i = 1; i < matrixArr.length; i++) {
			for (int j = 1; j < matrixArr[0].length; j++) {

				System.out.print(matrixArr[i][j] + " ");
			}
			System.out.println();
		}
	}

	public void printMatrix(Matrix distanceMatrix) {
		int counter = 0;
		int arrayDim = distanceMatrix.getColumnDimension();
		for (int i = 0; i < arrayDim; i++) {
			for (int j = 0; j < arrayDim; j++) {
				if (distanceMatrix.get(i, j) != 0
				// // && adjMatrix[i][j] < 1
				) {
					counter++;
					System.out.print(distanceMatrix.get(i, j) + "\t");
				}
			}
			System.out.println();
		}

		System.out.println();
		// System.out.println("counter:- " + counter);
	}

	private HashMap FileReader(File fileName) {
		String line = null;
		String[] strArray = null;
		ArrayList<Double> geneValues = null;

		HashMap<Integer, Gene> geneMap = new HashMap<Integer, Gene>();
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(
					fileName));
			while ((line = bufferedReader.readLine()) != null) {
				strArray = line.split("\\s+");

				// System.out.println((strArray.length < 2));
				if (strArray.length <= 2) {

					geneCount = Integer.parseInt(strArray[0]);
					attributeCount = Integer.parseInt(strArray[1]);
				} else {
					int geneId = Integer.parseInt(strArray[0]);
					int GroundTruth = Integer.parseInt(strArray[1]);
					geneValues = new ArrayList<Double>();
					int i = 2, j = 0;
					while (i < strArray.length) {
						// System.out.println(Double.parseDouble(strArray[i]));
						geneValues.add(Double.parseDouble(strArray[i]));
						i++;
						j++;
					}
					Gene geneObj = new Gene(geneId, GroundTruth, geneValues);
					geneMap.put(geneId, geneObj);
				}

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return geneMap;
	}

	private double[][] getDistanceMatrix(HashMap<Integer, Gene> geneMap) {
		double[][] matrixArr = new double[geneCount + 1][geneCount + 1];
		int counter = 0;
		for (HashMap.Entry<Integer, Gene> outerEnt : geneMap.entrySet()) {
			int i = outerEnt.getKey();
			for (HashMap.Entry<Integer, Gene> innerEnt : geneMap.entrySet()) {
				int j = innerEnt.getKey();
				double distance = getEucledeanDistance(outerEnt.getValue()
						.getGeneValues(), innerEnt.getValue().getGeneValues());
				matrixArr[i][j] = (Math.round(distance * 100000.0)) / 100000.0;
			}

		}
		return matrixArr;
	}

	private Matrix setDistanceMatrix(double[][] matrixArr) {
		Matrix distanceMatrix = new Matrix(matrixArr);
		return distanceMatrix;
	}

	private double getEucledeanDistance(ArrayList<Double> arraylist1,
			ArrayList<Double> arraylist2) {
		double sum = 0;
		double root = 0;
		for (int i = 0; i < arraylist1.size(); i++) {
			sum = sum + Math.pow((arraylist1.get(i) - arraylist2.get(i)), 2);
		}
		root = Math.sqrt(sum);
		return root;
	}

	public void driver(File fileName) {
		HashMap<Integer, Gene> geneMap = FileReader(fileName);
		double[][] matrixArr = (getDistanceMatrix(geneMap));
		Matrix distanceMatrix = setDistanceMatrix(matrixArr);
		printMatrix(distanceMatrix);
	}

	public static void main(String[] args) {
		DistanceMatrix obj = new DistanceMatrix();
		File fileName = new File(
				"/home/jagvir/DataMiningHierarchicalClusteringAlgorithm/dataFiles/test.txt");
		obj.driver(fileName);
		ArrayList<Double> arry1 = new ArrayList<Double>();
		ArrayList<Double> arry2 = new ArrayList<Double>();
		arry1.add(2.0);
		arry1.add(4.0);
		// arry1.add(5.0);
		// arry1.add(6.0);
		// arry1.add(7.0);

		arry2.add(6.0);
		arry2.add(7.0);
		// arry2.add(9.0);
		// arry2.add(1.0);
		// arry2.add(15.0);
		// System.out.println(obj.getEucledeanDistance(arry1, arry2));
	}
}
