package com.utilities;

import java.util.*;

public class Gene {

	private int geneId;
	private ArrayList<Double> geneValues;
	private int GroundTruth;

	public Gene(int geneId, int GroundTruth, ArrayList<Double> geneValues) {
		this.geneId = geneId;
		this.geneValues = geneValues;
		this.GroundTruth = GroundTruth;
	}

	public int getClusterId() {
		return geneId;
	}

	public void setClusterId(int clusterId) {
		this.geneId = clusterId;
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
