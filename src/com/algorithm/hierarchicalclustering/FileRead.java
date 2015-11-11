package com.algorithm.hierarchicalclustering;

import java.util.*;
import java.io.*;
import Jama.*;

import com.utilities.Gene;

public class FileRead {

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
					int clusterId = Integer.parseInt(strArray[0]);
					int GroundTruth = Integer.parseInt(strArray[1]);
					geneValues = new ArrayList<Double>();
					int i = 2, j = 0;
					while (i < strArray.length) {
						// System.out.println(Double.parseDouble(strArray[i]));
						geneValues.add(Double.parseDouble(strArray[i]));
						i++;
						j++;
					}
					Gene geneObj = new Gene(clusterId, GroundTruth, geneValues);
					geneMap.put(clusterId, geneObj);
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

	private void getDistanceMatrix(HashMap<Integer, Gene> geneMap) {
		for (HashMap.Entry<Integer, Gene> ent : geneMap.entrySet()) {

		}

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

	public static void main(String[] args) {
		FileRead obj = new FileRead();
		File fileName = new File(
				"/home/jagvir/DataMiningHierarchicalClusteringAlgorithm/dataFiles/iyer.txt");
		obj.printMap(obj.FileReader(fileName));
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
