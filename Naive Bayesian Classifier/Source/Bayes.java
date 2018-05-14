package com.naive;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Bayes {
	static HashMap<String, Integer> hm = new HashMap<String, Integer>();
	static ArrayList<ClassMap> arrh = new ArrayList<ClassMap>();
	static ArrayList<String> unique = new ArrayList<>();
	static String c;
	static int length;

	static File g[];

	public static void main(String[] args) throws FileNotFoundException {
		String trainingPath = args[0];
		String testingPath = args[1];
		File f = new File(trainingPath);
		g = f.listFiles();
		System.out.println("Classes:");
		for (int v = 0; v < g.length; v++) {
			System.out.println(g[v].getName());
		}
		listgetfile(f);
		Testfn fn = new Testfn();
		fn.findprob(testingPath);

	}

	static int total = 0;

	private static void listgetfile(File f) throws FileNotFoundException {

		for (File file : f.listFiles()) {

			if (file.isDirectory()) {
				c = file.getName();

				length = file.listFiles().length;
				total += length;
				listgetfile(file);
				ClassMap ah = new ClassMap();

				ah.setLength(length);
				ah.setC(c);
				ah.setHm((new HashMap<String, Integer>(hm)));
				ah.setCount(count);
				count = 0;
				arrh.add(ah);
				hm.clear();
			} else {
				openFile(file.getPath());
			}
		}

	}

	static int count = 0;

	private static void openFile(String path) throws FileNotFoundException {
		String str;
		File file = new File(path);
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		ArrayList<String> dl = new ArrayList<String>();
		ArrayList<String> after = new ArrayList<String>();
		removestop rs = new removestop();
		try {
			while ((str = br.readLine()) != null) {
				if (str.contains("Lines:")) {
					break;
				}
			}
			while ((str = br.readLine()) != null) {
				dl.add(str.trim().toLowerCase());
			}
			fr.close();
			after = rs.remove(dl);
			count += after.size();
			freq(after);
			after = null;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void freq(ArrayList<String> after) {

		for (int i = 0; i < after.size(); i++) {
			if ((hm.get(after.get(i))) == null) {
				hm.put(after.get(i), 1);
				if (!unique.contains(after.get(i))) {
					unique.add(after.get(i));
				}
			} else {
				int value = hm.get(after.get(i));
				hm.put(after.get(i), value + 1);
			}
		}

	}

}
