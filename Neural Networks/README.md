Defination: Implemented the backpropagation algorithm for Neural Networks and tested it on various real world datasets.
Assumptions:
1)	Function used: Sigmoid.
2)	Training data is randomly sampled from the dataset.
3)	Error used: Mean Square error.
4)	Termination condition is either Max number of iteration or error tolerance is met.

Prerequisites:
For Preprocessing::

1) converts all non-binary values to numerical and standardizes them according to below formula::

	x = (x-mean)/standard Deviation;

2) Drop the Dummy Attribute in Housing csv file named 
	
	4. CHAS      Charles River dummy variable (= 1 if tract bounds river; 0 otherwise)

3) scaling using follwing formula::
	
	x = (x-min(column))/(max(column)-min(column))

For Neural Net::

1) Use the Sigmoid as Activation Function

2) Termination condtion is either Max number of Epoch which is 500 or error_tolerance is met

3) learing rate is set to 1	

4) for Housing data where class values are more than 2(not feasible to have classification), regression is used

Experienment::

1) for Housing dataset, Error tolerance near 0.037 gives best accuracy

2) for Iris dataset, Error tolerance near 0.07 gives best accuracy

3) for Adult dataset, Error tolerance near 0.08 gives best accuracy

More complex Network, Less Accuracy

Best Accuracy is observed in Network, with the parameter as 2 hidden layer 3 and 4 Neurons in corresponding layers. 


Pre-processing
Inputs: 
1.	File from where the data is to be read.
2.	File from where the data is to be stored.

•	This information is stored in Arraylist statically. Whenever the program reads the new instance it will match with the values of the list and store its position as its own value. In this way, categorical data is converted into numerical.
•	Numerical data is converted using mean and standard deviation.
•	If it encounters “?” and “ ”, it will ignore that row, and read the next line.
•	The final output is kept as it is. That is a regression problem. It is not converted to binary variable.
Training:
Inputs:
•	File from where the data is stored i.e. the output file from the preprocessing stage.
•	Training Size
•	Maximum number of iterations
•	Number of hidden layers
•	For every hidden layer, number of neurons

1)	The program will terminate when either it reaches max iteration or error tolerance is met
2)	Learning rate is  0.9
3)	Accuracy is good when the number of layers are more, but not very large.
4)	Also, optimal number of iteration = 200
Testing:
•	After training given number of instances and iterating for given iterations, a network will be set which contain all the updated weights.
•	In this step, attributes of new instance will be taken as input. Also, we know the actual output from the dataset. 
•	We will predict the final output with our trained network and given new attributes, and find the output. This will be our predicted output.
•	Error is calculated using Mean Square Error.

Output:
For every layer (excluding output) it will print
1)	The Layer numbers
2)	Weights of every neurons in that layer
3)	Weight of the bias in that layer (shown in last element of every layer.)




DataSets:

[Iris Data]( https://archive.ics.uci.edu/ml/datasets/Iris)

[Car Data](https://archive.ics.uci.edu/ml/datasets/Car+Evaluation)

[Adult Data]( https://archive.ics.uci.edu/ml/datasets/Census+Income)

Run:

The BackPropagation algorithm was implemented using Java in Eclipse IDE.
The program will compile in the IDE with no errors. If IDE is not used, can compile using the
following command in the cmd/terminal:

Source folder contains all required java files 

1) javac *.java //will compile all the .java files




------------------------------------------------Preprocessing Data------------------------------------------------|
Command Line Arguments:: InputFile OutputFile

Run the following commands for different dataset

1) java PreProcessing data/iris.data.csv data/preProcessed_iris.csv 
O/P::
File Created :: preProcessed_iris.csv
lenght of file:  150

2) java PreProcessing data/adult.data.csv data/preProcessed_adult.csv
O/P::
File Created :: preProcessed_adult.csv
lenght of file:  30162

3)java PreProcessing data/car.data.csv data/preProcessed_car.csv
O/P::
File Created :: preProcessed_car.csv
lenght of file:  1728

------------------------------------------------Running ANN----------------------------------------------------------|

Command Line Arguments:: InputFile TrainingSize MaxIteration NumberofHiddenLayer NumberofNeurons

Run the following Commands for different dataset

1) java BackPropagation data/preProcessed_iris.csv 85 150 2 5 3

2) java BackPropagation data/preProcessed_adult.csv 85 250 2 8 5

3) java BackPropagation data/preProcessed_car.csv 85 200 3 8 5 3

O/P ::

Layer : 0

Neuron : 1 Weights : 0.8980874693051545   0.9696431784638665   0.24344471669044662   -0.6868125213991212   -0.16474968266745119   
Neuron : 2 Weights : 0.32386532466823637   0.9590136249589855   -0.6032750957274909   0.9446990256432639   0.16772667822777096   
Neuron : 3 Weights : 0.6189911435923904   0.7708566986294049   -0.4054299618349941   0.07041386604271697   0.002960466783106787   
Neuron : 4 Weights : -0.8682236675013352   0.025874711694425095   -0.950275235934298   0.15988747789848512   -0.7861320295635743   
Neuron : 5 Weights : 1.0071260969081846 0.3048471857218805 0.34818365733878553 -0.06857528962523703 0.12577189743914252 

Layer : 1


Neuron : 1 Weights : -0.6311925202780356   0.558087797543726   -0.231690722165752   
Neuron : 2 Weights : 0.8227075376757668   -0.0356195848780562   -0.5643027776736649   
Neuron : 3 Weights : 0.7455612471607759   -0.8904711799987248   -0.3119847331540146   
Neuron : 4 Weights : -0.29279634334203775   0.285578762993499   -0.0427715902047001   
Neuron : 5 Weights : -0.5563122529209789   -0.7083301749293778   0.8095542445666428   
Neuron : 6 Weights : -0.07786499563221237   0.5243400481802402   0.9713588367336866   

Layer : 2


Neuron : 1 Weights : -0.12296466626248478   -0.17557001236098133   
Neuron : 2 Weights : -0.34460491377879654   0.8147659265915645   
Neuron : 3 Weights : -0.5453314870852068   0.04519428057808614   
Neuron : 4 Weights : -0.3253475436757736   0.010246840662981475   

Layer : 3


Neuron : 1 Weights : 0.8451877061253619   
Neuron : 2 Weights : 0.023841020146797558   
Neuron : 3 Weights : 2.2145702971850745   

Total Training Error : 0.006299212598425202
Training Accuracy : 68.50393700787401

Total Testing Error:0.0034782608695652197
Testing Accuracy : 82.6086956521739







 
Consider this network (ignore the weights)
Weights will be randomly initialized.
Output will be shown as 
Layer 0
Neuron 1 weights: W41, W51
Neuron 2 weights: W42, W52
Neuron 3 weights: W43, W53
Bias Weight: W40, W50

Layer 1
Neuron 1 weights: W64
Neuron 2 weights: W65
Bias Weight: W60

Experiment: 

1)	Dataset : Car.data.csv
MaxIteration : 200
Hidden layers: 2 (5, 3)
	
 


2)	Dataset : adult.data.csv
MaxIteration : 150
Hidden layers: 2 (8, 5)


 
 

3)	Dataset : iris.data.csv
MaxIteration : 150
Hidden layers: 3 (5, 3, 2)

 



Reference :
1. https://visualstudiomagazine.com/articles/2014/01/01/how-to-standardize-data-for-neuralnetworks.
Aspx
2. https://mattmazur.com/2015/03/17/a-step-by-step-backpropagation-example/
3. Data pre-processing
https://www.mimuw.edu.pl/~son/datamining/DM/4-preprocess.pdf
4. http://neuralnetworksanddeeplearning.com/chap2.html
