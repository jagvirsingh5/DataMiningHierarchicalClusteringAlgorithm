package com.algorithm.hierarchicalclustering;

import java.util.*;
import java.io.*;

public class FileRead {

	/**
	 * @param fileName
	 *            where fileName is the location of the file in the folder
	 */
	public void FileReader(File fileName) {
		String line = null;
		String[] strArray = null;
		HashMap<Integer, ArrayList> geneMap = new HashMap<Integer, ArrayList>();
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(
					fileName));
			while ((line = bufferedReader.readLine()) != null) {
				strArray = line.split("\\s+");

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < strArray.length; i++) {

		}
	}

	public static void main(String[] args) {
		FileRead obj = new FileRead();
		File fileName = new File("iyer.txt");
		obj.FileReader(fileName);

	}
}
