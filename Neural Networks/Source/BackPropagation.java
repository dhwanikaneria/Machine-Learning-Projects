package backPropagation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

public class BackPropagation {

	public static void main(String[] args) {
		String inputDataPath = args[0];
		Double trainingPercentage = Double.parseDouble(args[1]);
		Integer maxIteration = Integer.parseInt(args[2]);
		Integer NumberOfHidden = Integer.parseInt(args[3]) + 1;
		int[] hL = new int[NumberOfHidden];

		for (int i = 0; i < NumberOfHidden - 1; i++) {
			hL[i] = Integer.parseInt(args[4 + i]);
		}
		hL[NumberOfHidden - 1] = 1;
		// Read Data
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(inputDataPath));
			ArrayList<Double[]> data = new ArrayList<Double[]>();
			String[] dataString;
			String str;
			ArrayList<Double[]> training = new ArrayList<Double[]>();
			ArrayList<Double[]> testing = new ArrayList<Double[]>();
			int inputs = training.get(0).length - 2;
			ArrayList<ArrayList<ArrayList<Double>>> w1 = new ArrayList<ArrayList<ArrayList<Double>>>(); // Storing
																										// Weights and
																										// Value of
																										// Neuron Nodes

			while ((str = br.readLine()) != (null)) {
				str = str.trim();
				str = str.replaceAll("\\s+", "");
				dataString = str.split(",");
				Double[] datatemp = new Double[dataString.length];
				for (int i = 0; i < dataString.length; i++) {
					datatemp[i] = Double.parseDouble(dataString[i]);
				}

				if (datatemp.length != 0) {
					data.add(datatemp);
				}
			}

			// Partition test and training data
			int part = (int) (data.size() * trainingPercentage / 100);
			Collections.shuffle(data);
			int h = 0;
			for (int i = 0; i < data.size(); i++) {
				if (i < part) {
					training.add(data.get(i));
				} else {
					testing.add(data.get(h));
					h++;
				}
			}
			// Assign Initial Weight to neurons
			for (int i = 0; i < NumberOfHidden + 1; i++) {
				ArrayList<ArrayList<Double>> a = new ArrayList<ArrayList<Double>>();
				if (i == 0) {

					for (int b = 0; b <= inputs; b++) {
						ArrayList<Double> c = new ArrayList<>();

						c.add(training.get(0)[b]);
						for (int e = 0; e < hL[i]; e++) {
							c.add((Double) (Math.random() * 2 - 1));
							// c.add(0.5);
						}
						a.add(c);
					}
					for (int d = 0; d < hL[i]; d++) {
						ArrayList<Double> c = new ArrayList<>();
						c.add(1.0);
						c.add((Double) (Math.random() * 2 - 1));
						// c.add(0.5);
						a.add(c);
					}
					w1.add(a);
				} else if (NumberOfHidden == i) {
					for (int d = 0; d < hL[i - 1]; d++) {

						ArrayList<Double> c = new ArrayList<>();
						c.add(1.0);
						c.add((Double) (Math.random() * 2 - 1));
						// c.add(0.5);
						a.add(c);
					}
					w1.add(a);
				} else {
					for (int d = 0; d < hL[i - 1]; d++) {
						ArrayList<Double> c = new ArrayList<>();
						c.add(1.0);
						for (int e = 0; e < hL[i]; e++) {
							c.add((Double) (Math.random() * 2 - 1));
							// c.add(0.5);

						}
						a.add(c);
					}
					for (int d = 0; d < hL[i]; d++) {
						ArrayList<Double> c = new ArrayList<>();
						c.add(1.0);
						c.add((Double) (Math.random() * 2 - 1));
						// c.add(0.5);
						a.add(c);
					}
					w1.add(a);
				}
			}

			// Forward Pass
			boolean flag = false;
			for (int r = 0; r < training.size(); r++) {
				for (int k = 0; k <= inputs; k++) {
					w1.get(0).get(k).set(0, training.get(r)[k]);
				}
				for (int s = 0; s < maxIteration; s++) {
					for (int i = 1; i < w1.size(); i++) {
						for (int j = 0; j < hL[i - 1]; j++) {
							if (i == 1) {
								Double f = 0.0;
								for (int m = 0; m <= inputs; m++) {
									f += w1.get(0).get(m).get(0) * w1.get(0).get(m).get(j + 1);

								}
								f += w1.get(0).get(j + inputs + 1).get(0) * w1.get(0).get(j + inputs + 1).get(1);
								double sigmoid = 1 / (1 + Math.exp(-f));
								w1.get(i).get(j).set(0, sigmoid);
							} else {
								Double f = 0.0;
								for (int m = 0; m < hL[i - 2]; m++) {
									f += w1.get(i - 1).get(m).get(0) * w1.get(i - 1).get(m).get(j + 1);

								}

								f += w1.get(i - 1).get(j + hL[i - 2]).get(0) * w1.get(i - 1).get(j + hL[i - 2]).get(1);
								double sigmoid = 1 / (1 + Math.exp(-f));
								w1.get(i).get(j).set(0, sigmoid);

							}
						}
					}
					// Backward Pass
					ArrayList<Double[]> delta = new ArrayList<Double[]>(); // Storing Delta values
					int j = 0;
					Double[] tempd;
					for (int i = w1.size(); i > 1; i--) {
						tempd = new Double[hL[i - 2]];

						for (int k = hL[i - 2] - 1; k >= 0; k--) {
							if (i == w1.size()) {
								tempd[k] = (1 - w1.get(i - 1).get(k).get(0))
										* (training.get(r)[training.get(r).length - 1] - w1.get(i - 1).get(k).get(0))
										* w1.get(i - 1).get(k).get(0);

								Double threshold = 0.08;

								if ((tempd[k] >= -threshold) && (threshold >= tempd[k])) {
									flag = true;
									break;
								}

							} else {
								double f = 0.0;
								for (int l = 0; l < hL[i - 1]; l++) {
									f += (w1.get(i).get(l).get(0) * delta.get(j - 1)[l]);

								}
								tempd[k] = (w1.get(i - 1).get(k).get(0)) * (1 - w1.get(i - 1).get(k).get(0)) * f;
							}
						}
						if (flag) {
							break;
						}
						delta.add(tempd);
						j++;
					}
					if (flag) {
						break;
					}
					ArrayList<Double[]> deltareverse = new ArrayList<Double[]>();
					for (int i = delta.size() - 1; i >= 0; i--) {
						deltareverse.add(delta.get(i));
					}

					// Updating Weights
					for (int i = 0; i < w1.size() - 1; i++) {
						int m = 0;
						for (int j1 = 0; j1 < w1.get(i).size(); j1++) {
							if (w1.get(i).size() - hL[i] > j1) {
								for (int k = 0; k < hL[i]; k++) {
									w1.get(i).get(j1).set(k + 1, w1.get(i).get(j1).get(k + 1)
											+ 0.9 * deltareverse.get(i)[k] * w1.get(i).get(k).get(0));

								}
								m++;
							} else {

								w1.get(i).get(j1).set(1, w1.get(i).get(j1).get(0) * 0.9 * deltareverse.get(i)[j1 - m]
										+ w1.get(i).get(j1).get(1));
							}
						}
					}

				}

			}
			// Printing of Layer Weight
			for (int i = 0; i < w1.size() - 1; i++) {
				System.out.println("Layer : " + i + "\n");
				for (int j1 = 0; j1 < w1.get(i).size(); j1++) {
					if (i == 0) {
						if (j1 <= inputs) {
							System.out.print("Neuron : " + (j1 + 1) + " Weights : ");
							for (int g = 1; g < w1.get(i).get(j1).size(); g++) {
								System.out.print(w1.get(i).get(j1).get(g) + "   ");
							}
							System.out.println();
						} else {
							if (j1 == inputs + 1) {
								System.out.print("Neuron : " + (j1 + 1) + " Weights : ");
							}
							System.out.print(w1.get(i).get(j1).get(1) + " ");
						}
					} else {
						if (j1 <= hL[i - 1]) {
							System.out.println();
							System.out.print("Neuron : " + (j1 + 1) + " Weights : ");

						}
						if (j1 < hL[i - 1]) {
							for (int g = 1; g < w1.get(i).get(j1).size(); g++) {

								System.out.print(w1.get(i).get(j1).get(g) + "   ");
							}
						} else {
							System.out.print(w1.get(i).get(j1).get(1) + "   ");
						}

					}

				}
				System.out.println("\n");

			}

			// Test Training Data
			int count = 0;
			double ttrainerror = 0.0;
			for (int r = 0; r < training.size(); r++) {
				for (int k = 0; k <= inputs; k++) {
					w1.get(0).get(k).set(0, training.get(r)[k]);
				}
				for (int i = 1; i < w1.size(); i++) {
					for (int j = 0; j < hL[i - 1]; j++) {
						if (i == 1) {
							Double f = 0.0;
							for (int m = 0; m <= inputs; m++) {
								f += w1.get(0).get(m).get(0) * w1.get(0).get(m).get(j + 1);

							}
							f += w1.get(0).get(j + inputs + 1).get(0) * w1.get(0).get(j + inputs + 1).get(1);
							double sigmoid = 1 / (1 + Math.exp(-f));
							w1.get(i).get(j).set(0, sigmoid);
						} else {
							Double f = 0.0;
							for (int m = 0; m < hL[i - 2]; m++) {
								f += w1.get(i - 1).get(m).get(0) * w1.get(i - 1).get(m).get(j + 1);

							}

							f += w1.get(i - 1).get(j + hL[i - 2]).get(0) * w1.get(i - 1).get(j + hL[i - 2]).get(1);
							double sigmoid = 1 / (1 + Math.exp(-f));
							w1.get(i).get(j).set(0, sigmoid);

						}
					}
				}
				DecimalFormat dec = new DecimalFormat("0.0");
				double sigmoid = 1 / (1 + Math.exp(-training.get(r)[training.get(r).length - 1]));

				double o = Double.parseDouble(dec.format(sigmoid));
				double p = Double.parseDouble(dec.format(w1.get(w1.size() - 1).get(0).get(0)));

				if ((p - 0.1 <= o) && (o <= p + 0.2)) {
					count++;

				} else {
					double error = ((p - o) * (p - o));
					ttrainerror += error;
				}
			}
			System.out.println("Total Training Error : " + ttrainerror / (2 * training.size()));

			System.out.println("Training Accuracy : " + (count / (double) training.size() * 100));
			System.out.println();

			// Test Testing Data
			int count1 = 0;
			double ttesterror = 0.0;
			for (int r = 0; r < testing.size(); r++) {
				for (int k = 0; k <= inputs; k++) {
					w1.get(0).get(k).set(0, testing.get(r)[k]);
				}
				for (int i = 1; i < w1.size(); i++) {
					for (int j = 0; j < hL[i - 1]; j++) {
						if (i == 1) {
							Double f = 0.0;
							for (int m = 0; m <= inputs; m++) {
								f += w1.get(0).get(m).get(0) * w1.get(0).get(m).get(j + 1);

							}
							f += w1.get(0).get(j + inputs + 1).get(0) * w1.get(0).get(j + inputs + 1).get(1);
							double sigmoid = 1 / (1 + Math.exp(-f));
							w1.get(i).get(j).set(0, sigmoid);
						} else {
							Double f = 0.0;
							for (int m = 0; m < hL[i - 2]; m++) {
								f += w1.get(i - 1).get(m).get(0) * w1.get(i - 1).get(m).get(j + 1);

							}

							f += w1.get(i - 1).get(j + hL[i - 2]).get(0) * w1.get(i - 1).get(j + hL[i - 2]).get(1);
							double sigmoid = 1 / (1 + Math.exp(-f));
							w1.get(i).get(j).set(0, sigmoid);

						}
					}
				}
				DecimalFormat dec = new DecimalFormat("0.0");
				double sigmoid = 1 / (1 + Math.exp(-testing.get(r)[testing.get(r).length - 1]));
				double o = Double.parseDouble(dec.format(sigmoid));
				double p = Double.parseDouble(dec.format(w1.get(w1.size() - 1).get(0).get(0)));
				if ((p - 0.1 <= o) && (o <= p + 0.2)) {
					count1++;
				} else {
					double error = ((p - o) * (p - o));
					ttesterror += error;
				}
			}
			System.out.println("Total Testing Error:" + ttesterror / (2 * testing.size()));
			System.out.println("Testing Accuracy : " + (count1 / (double) testing.size() * 100));

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
