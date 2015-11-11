package com.utilities;

import java.util.*;
import java.io.*;

public class Gene {

	private int clusterId;
	private ArrayList<Double> geneValues;
	private int GroundTruth;

	public Gene(int clusterId, int GroundTruth, ArrayList geneValues) {
		this.clusterId = clusterId;
		this.geneValues = geneValues;
		this.GroundTruth = GroundTruth;
	}

	public int getClusterId() {
		return clusterId;
	}

	public void setClusterId(int clusterId) {
		this.clusterId = clusterId;
	}

	public ArrayList<Double> getGeneValues() {
		return geneValues;
	}

	public void setGeneValues(ArrayList<Double> geneValues) {
		this.geneValues = geneValues;
	}

	public int getTrueValue() {
		return GroundTruth;
	}

	public void setGroundTruth(int GroundTruth) {
		this.GroundTruth = GroundTruth;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
