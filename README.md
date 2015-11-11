#Number Prediction

Number Prediction is a Java application for predicting data using different technologies. 

##Input Data

Data analyzed is a log file containing certain values. To process data in Java log file is converted to CSV file. Here is the bash script for converting: [logToCsv.sh](logToCsv.sh). Some duplicate rows are deleted using Microsoft Excel. 
The list of columns of independent values in CSV file: 

* *numFlows*
* *flowLoadingTime*
* *numEvents*
* *collectionDuration*
* *numPrimitiveIncidents*
* *numFlowsInEvents*
* *resultSize*
* *numArtificialFileDownloadEvents*
* *numFileDownloadFlows*

The dependent value, which is obseved and predicted is *computationTime*. 
