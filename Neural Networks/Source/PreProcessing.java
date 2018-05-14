package backPropagation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class PreProcessing {

	public static void main(String[] args) {
		BufferedReader br = null;
		try {
			String inputDataPath = args[0];
			String outputDataPath = args[1];

			br = new BufferedReader(new FileReader(inputDataPath));

			FileWriter fw = new FileWriter(new File(outputDataPath));
			BufferedWriter bw = new BufferedWriter(fw);

			ArrayList<String[]> data = new ArrayList<String[]>();

			String[] datatemp;

			String str;
			boolean flag = false;

			// Read Data from file
			while ((str = br.readLine()) != (null)) {
				str = str.trim();
				str = str.replaceAll("\\s+", "");
				datatemp = str.split(",");
				if (datatemp.length != 0) {

					for (String s : datatemp) {

						if (s.equals("?") || s.equals("")) {
							flag = true;
							break;
						}
					}
					if (flag == false) {
						data.add(datatemp);

					}
					flag = false;
				}

			}

			ArrayList<ArrayList<String>> converter1 = new ArrayList<ArrayList<String>>();

			ArrayList<String> temp = new ArrayList<>();

			Boolean flag1[] = new Boolean[data.get(0).length];
			Arrays.fill(flag1, Boolean.FALSE);
			int[] r = new int[data.size() / 2];

			// Find Categorical Column
			for (int j = 0; j < data.get(0).length; j++) {
				for (int i = 0; i < data.size() / 2; i++) {
					r[i] = (int) Math.floor(Math.random() * data.size());
					if (data.get(r[i])[j].matches("^.*[a-zA-Z<>=]+.*")) {
						flag1[j] = true;
						break;
					}
				}
			}

			for (int i = 0; i < data.get(0).length; i++) {
				if (flag1[i]) {
					temp = new ArrayList<>();
					temp.add(data.get(0)[i]);
					converter1.add(temp);
				} else {
					converter1.add(null);
				}
			}
			// Find Mean for Numerical Data
			double mean[] = new double[data.get(0).length];
			for (int k = 0; k < data.get(0).length; k++) {
				for (int i = 0; i < data.size(); i++) {
					String column = data.get(i)[k];
					if (flag1[k]) {
						if (converter1.get(k).contains(column)) {
						} else {
							converter1.get(k).add(column);
						}
					} else {

						mean[k] += Double.parseDouble(column); // numerical
					}
				}
				mean[k] = mean[k] / data.size();
			}
			// Find Standard Deviation
			double stdDev[] = new double[data.get(0).length];
			for (int k = 0; k < data.get(0).length; k++) {
				for (int i = 0; i < data.size(); i++) {
					String column = data.get(i)[k];
					if (!flag1[k]) {
						stdDev[k] += (Double.parseDouble(column) - mean[k]) * (Double.parseDouble(column) - mean[k]);
					}
				}
				stdDev[k] /= data.size();
			}

			ArrayList<String[]> data2 = new ArrayList<String[]>();
			// Processed data and store into file
			for (int i = 0; i < data.size(); i++) {
				String[] row = data.get(i);

				for (int j = 0; j < row.length; j++) {

					if (flag1[j]) {
						row[j] = Integer.toString(converter1.get(j).indexOf(row[j]) + 1);

					} else {
						row[j] = Double.toString((Double.parseDouble(row[j]) - mean[j]) / stdDev[j]);
					}
					String A = (j != row.length - 1) ? (row[j] + ", ") : row[j];
					bw.write(A);
				}
				String A = (i != data.size() - 1) ? ("\n") : ("");
				bw.write(A);
				data2.add(row);
			}

			bw.close();
			System.out.println("File Created : " + outputDataPath);
			System.out.println("lenght of file : " + data.size());
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
