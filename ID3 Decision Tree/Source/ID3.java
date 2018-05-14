package Test2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class ID3 {
	static int total_node = 0;
	static int leaf_node = 0;
	static boolean testing_flag = false;
	static boolean validation_flag = false;
	static int prune_count, prune_factor;

	public static void main(String[] args) {

		BufferedReader br = null;
		try {
			String training_set_path = args[0];
			String validation_set_path = args[1];
			String test_set_path = args[2];
			br = new BufferedReader(new FileReader(training_set_path));

			String[] attrbi = br.readLine().split(",");
			ArrayList<String[]> data = new ArrayList<String[]>();

			String[] datatemp;

			String str;
			while ((str = br.readLine()) != (null)) {

				datatemp = str.split(",");
				if (datatemp.length != 0) {
					data.add(datatemp);
				}
			}
			Node root = new Node();
			total_node++;
			root.lable = -1;
			root.data = data;
			root.attri = attrbi;
			root.depth = 0;
			root.num = 1;
			Count(root, data, attrbi.length - 1);
			Bind(root);
			print(root, "");

			System.out.println();
			System.out.println("Pre-Pruned Accuracy");
			System.out.println("--------------------------------------------");
			System.out.println("Number of training instances = " + data.size());
			System.out.println("Number of training attributes = " + (attrbi.length-1));

			System.out.println("Total number of nodes in the tree = " + total_node);
			System.out.println("Number of leaf nodes in the tree = " + leaf_node);
			System.out.print("Accuracy of the model on the training data = ");
			calculate_Accuracy(root, data);

			System.out.println();
			validation_flag = true;
			calculate_Accuracy(root, read_data(validation_set_path, false));

			System.out.println();
			testing_flag = true;
			calculate_Accuracy(root, read_data(test_set_path, true));

			prune_factor = (int) (Double.parseDouble(args[3]) * total_node) / 100;

			prune_factor = (prune_factor % 2) == 0 ? prune_factor : prune_factor + 1;

			System.out.println("Number of Nodes in total " + total_node + " and those to be pruned :: " + prune_factor
					+ "(e.g " + Double.parseDouble(args[3]) + "% of " + total_node + ")");
			System.out.println("\nPruned Tree::");

			prune(root, prune_factor, total_node);
			if (root.lable == -1) {
				print(root, "");
			}
			total_node = 1;
			leaf_node = 0;
			nodecount(root);

			System.out.println("\n\nPost-Pruned Accuracy");
			System.out.println("-------------------------------------");
			System.out.println("Number of training instances = " + data.size());
			System.out.println("Number of training attributes = " + (root.attri.length-1));
			System.out.println("Total number of nodes in the pruned tree = " + total_node);
			System.out.println("Number of leaf nodes in the pruned tree = " + leaf_node);

			testing_flag = false;
			validation_flag = false;
			System.out.print("Accuracy of the model on the training data = ");
			calculate_Accuracy(root, data);

			System.out.println();
			validation_flag = true;
			calculate_Accuracy(root, read_data(validation_set_path, false));

			System.out.println();
			testing_flag = true;
			calculate_Accuracy(root, read_data(test_set_path, true));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void nodecount(Node root) {
		if (root.left != null && root.right != null) {
			nodecount(root.left);
			total_node++;
			nodecount(root.right);
			total_node++;
		} else {

			leaf_node++;
		}

	}

	private static void prune(Node root, int prune_factor2, int total_node2) {
		int[] r = new int[prune_factor2];

		// Generating random node numbers to prone
		for (int i = 0; i < prune_factor2; i++) {
			r[i] = (int) (Math.random() * (total_node2 - 1)) + 1;
		}
		trim(root, r);

	}

	private static void trim(Node n, int[] r) {
		if (n.left == null && n.right == null) {
			return;
		} else {
			for (int i = 0; i < r.length; i++) {
				if (n.num == r[i]) {
					n.left = null;
					n.right = null;

					if (n.count0 > n.count1) {
						n.lable = 0;
					} else {
						n.lable = 1;
					}

				}

			}
			if (!(n.left == null && n.right == null)) {
				trim(n.left, r);
				trim(n.right, r);
			}
		}

	}

	private static void print(Node root, String string) {

		String s;
		s = string + root.name + " = 0 : ";
		s += "";
		if (root.left.left == null) {
			s += root.left.lable;
			System.out.println(s);
		} else {
			System.out.println(s);
			if (!Objects.equals(root.left.name, null)) {
				print(root.left, " | " + string);
			}
		}

		s = string + root.name + " = 1 : ";
		if (root.right.right == null) {
			s += root.right.lable;
			System.out.println(s);
		} else {
			System.out.println(s);
			if (!Objects.equals(root.right.name, null)) {

				print(root.right, " | " + string);
			}
		}

	}

	private static void Bind(Node root) {
		try {
			if (root.depth < root.attri.length) {
				if (root.count0 > 0 && root.count1 > 0) {
					FindSplitter(root);
					root.lable = -1;
					root.left = new Node();
					root.left.parent = root;
					total_node++;
					root.left.depth = root.depth + 1;
					root.left.num = root.num + 1;
					root.right = new Node();
					root.right.parent = root;
					total_node++;
					root.right.depth = root.depth + 1;
					root.right.num = root.num + 2;
					root.left.attri = give_attr(root);
					root.right.attri = give_attr(root);
					root.left.data = give_data(root, "0");
					root.right.data = give_data(root, "1");
					Count(root.left, root.left.data, root.left.attri.length - 1);
					Bind(root.left);
					Count(root.right, root.right.data, root.right.attri.length - 1);
					Bind(root.right);
				} else {
					leaf_node++;

					if (root.count0 > 0) {
						root.lable = 0;
					} else {
						root.lable = 1;
					}

				}
			}
		} catch (java.lang.StackOverflowError e) {

		}

	}

	private static ArrayList<String[]> give_data(Node root, String string) {
		ArrayList<String[]> data = new ArrayList<String[]>();

		for (String[] str : root.data) {
			if (str[root.si].equals(string)) {
				data.add(str);

			}
		}

		return data;
	}

	private static String[] give_attr(Node root) {
		String[] array = new String[root.attri.length];
		for (int i = 0; i <= root.attri.length - 1; i++) {
			array[i] = root.attri[i];
			if (i == root.si) {
				array[i] = "0";
			}

		}
		return array;
	}

	private static void FindSplitter(Node root) {
		double p0 = (double) root.count0 / (root.count0 + root.count1);
		double p1 = (double) root.count1 / (root.count0 + root.count1);
		double pe = -(p0 * (Math.log10(p0) / Math.log10(2))) - (p1 * (Math.log10(p1) / Math.log10(2)));
		double[] IG = new double[root.attri.length - 1];
		int leaf = 0;
		for (int i = 0; i <= root.attri.length - 2; i++) {

			if (!root.attri[i].equals("0")) {
				int countX00 = 0;
				int countX01 = 0;
				int countX10 = 0;
				int countX11 = 0;
				for (int j = 0; j < root.data.size(); j++) {
					if (root.data.get(j)[root.attri.length - 1].equals("0")) {
						if (root.data.get(j)[i].equals("0")) {
							countX00++;
						} else {
							countX10++;
						}
					} else if (root.data.get(j)[root.attri.length - 1].equals("1")) {
						if (root.data.get(j)[i].equals("0")) {
							countX01++;
						} else {
							countX11++;
						}
					}
				}
				double H00 = 0;
				double H01 = 0;
				double H0 = 0;
				if (countX00 != 0 && countX01 != 0) {
					H00 = (double) countX00 / (countX00 + countX01);
					H01 = (double) countX01 / (countX00 + countX01);
					H0 = -(H00 * (Math.log10(H00) / Math.log10(2))) - (H01 * (Math.log10(H01) / Math.log10(2)));
				}

				double H10 = 0;
				double H11 = 0;
				double H1 = 0;
				if (countX10 != 0 && countX11 != 0) {
					H10 = (double) countX10 / (countX10 + countX11);

					H11 = (double) countX11 / (countX10 + countX11);
					H1 = -(H10 * (Math.log10(H10) / Math.log10(2))) - (H11 * (Math.log10(H11) / Math.log10(2)));
				}

				IG[i] = pe - ((double) (countX00 + countX01) / (root.count0 + root.count1)) * H0
						- ((double) (countX10 + countX11) / (root.count0 + root.count1)) * H1;
			} else {
				leaf++;

			}

		}
		if (leaf == root.attri.length - 2) {
		}
		double maxig = 0;
		int index = -1;
		for (int z = 0; z <= IG.length - 1; z++) {
			if (maxig < IG[z]) {
				maxig = IG[z];
				index = z;
			}

		}
		if (index == -1) {
			if (root.count0 > root.count1) {
				root.lable = 0;
			} else {
				root.lable = 1;
			}
			leaf_node++;
		} else {
			root.si = index;
			root.name = root.attri[index];
		}
	}

	public static void Count(Node node, ArrayList<String[]> data, int index) {

		for (String[] p1 : data) {
			// System.out.println(p1);

			if (p1[index].equals("0")) {
				node.count0++;
			} else {
				node.count1++;
			}
		}

	}

	static void calculate_Accuracy(Node root_Node, ArrayList<String[]> data) {
		int miss_class_count = 0;
		for (String[] datarow : data) {
			int node_class_label = check_class_label(root_Node, datarow);

			if (!(datarow[root_Node.attri.length - 1].equals(String.valueOf(node_class_label)))) {
				miss_class_count++;

			}
		}
		if (validation_flag && !testing_flag) {
			System.out.print("Accuracy of the model on the validation data = ");
		}
		if (testing_flag) {
			System.out.print("Accuracy of the model on the testing data = ");
		}
		System.out.println((((data.size() - miss_class_count) / (float) data.size()) * 100) + "%");
	}

	private static int check_class_label(Node root_Node, String[] is) {
		if (root_Node.lable != -1) {

			return root_Node.lable;
		} else {
			if (is[root_Node.si].equals("0")) {
				if (root_Node.left != null)
					return check_class_label(root_Node.left, is);
				else {
					return root_Node.lable;
				}
			} else {
				if (root_Node.right != null)
					return check_class_label(root_Node.right, is);
				else {
					return root_Node.lable;
				}
			}
		}
	}

	static ArrayList<String[]> read_data(String filename, boolean flag) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader(filename));

		String[] attrbi = br.readLine().split(",");
		ArrayList<String[]> data = new ArrayList<String[]>();

		String[] datatemp;

		String str;
		while ((str = br.readLine()) != (null)) {

			datatemp = str.split(",");
			if (datatemp.length != 0) {
				data.add(datatemp);
			}
		}

		br.close();
		if (flag == true) {
			System.out.println("Number of testing instances = " + data.size());
			System.out.println("Number of testing attributes = " + attrbi.length);
		} else {
			System.out.println("Number of validation instances = " + data.size());
			System.out.println("Number of validation attributes = " + attrbi.length);
		}
		return data;
	}

}
