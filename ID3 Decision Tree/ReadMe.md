
Defination: Build a binary decision tree classifier using the ID3 algorithm.

Assumptions:
1) The data used to test your implementation will contain only Boolean (0 or 1) attributes and Boolean (0 or 1) class values.
2) There will be no missing data or attributes.
3) The first row of the dataset will contain column names and each non-blank line after that will contain a new data instance.
4) The last column would contain the class labels.


Run:

The ID3 algorithm was implemented using Java in Eclipse IDE.
The program will compile in the IDE with no errors. If IDE is not used, can compile using the
following command in the cmd/terminal:

Source folder contains all required java files 

1) javac *.java //will compile all the .java files



Command Line Arguments:: TrainingData ValidationData TestingData PruningFactor

2)	Run the Model for DataSet1::
	java ID3 data_sets1/training_set.csv data_sets1/validation_set.csv data_sets1/test_set.csv 20.0
	

	OR
	Run the Model for DataSet2::
	java ID3 data_sets2/training_set.csv data_sets2/validation_set.csv data_sets2/test_set.csv 20.0

	(You can Replace the 20.0 With whatever fraction of the total number of nodes you may want to test with).

You can see the Output of the program on both the Dataset in the file attached, OUTPUT1.txt and OUTPUT2.txt, respectively.

O/P Order::

1)Pre-Pruned Tree

2)Pre-Pruned Accuracy

3)Post-Pruned Tree

4)Post-Pruned Accuracy

OutPut:
[OutPut for Dataset1](https://github.com/dhwanikaneria/Machine-Learning-Projects/blob/master/ID3%20Decision%20Tree/Output1.txt)
[OutPut for Dataset2](https://github.com/dhwanikaneria/Machine-Learning-Projects/blob/master/ID3%20Decision%20Tree/Output2.txt)


DataSets:
[Data Set1](https://github.com/dhwanikaneria/Machine-Learning-Projects/tree/master/ID3%20Decision%20Tree/data_sets1)
[Data set2](https://github.com/dhwanikaneria/Machine-Learning-Projects/tree/master/ID3%20Decision%20Tree/data_sets2)