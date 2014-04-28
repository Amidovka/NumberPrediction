#!/bin/bash
INPUT="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )/compTimeStat.log"
OUTPUT="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )/$(basename $INPUT | cut -d'.' -f1).csv"

# field names
s=1
dataSetNumber=
numFlows=
flowLoadingTime=
numPrimitiveIncidents=
numEvents=
numFlowsInEvents=
resultSize=
numArtificialFileDownloadEvents=
numFileDownloadFlows=
numFileDownloadFlowsInEvents=

computationTime=
echo -n "Processing..."
# empty output file
>$OUTPUT

# read input line by line
while IFS= read line
do
   # get data
    dataSetNumber=$(cut -d, -f5<<<"$line" | cut -d":" -f2)
    numFlows=$(cut -d, -f7<<<"$line" | cut -d":" -f2)
    flowLoadingTime=$(cut -d, -f8<<<"$line" | cut -d":" -f2)
    # numCollections=$(cut -d, -f11<<<"$line" | cut -d":" -f3)
    # CollectionDuration=$(cut -d, -f12<<<"$line" | cut -d":" -f2 | cut -d} -f1)
    numPrimitiveIncidents=$(cut -d, -f13<<<"$line" | cut -d":" -f2)
    numEvents=$(cut -d, -f14<<<"$line" | cut -d":" -f2)
    numFlowsInEvents=$(cut -d, -f15<<<"$line" | cut -d":" -f2)
    resultSize=$(cut -d, -f16<<<"$line" | cut -d":" -f2)
    numArtificialFileDownloadEvents=$(cut -d, -f17<<<"$line" | cut -d":" -f2)
    numFileDownloadFlows=$(cut -d, -f18<<<"$line" | cut -d":" -f2)
    numFileDownloadFlowsInEvents=$(cut -d, -f19<<<"$line" | cut -d":" -f2 | cut -d} -f1)

    computationTime=$(cut -d, -f6<<<"$line" | cut -d":" -f2) 
   # write csv formatted output
    echo "${dataSetNumber},${numFlows},${flowLoadingTime},${numPrimitiveIncidents},${numEvents},${numFlowsInEvents},${resultSize},${numArtificialFileDownloadEvents},${numFileDownloadFlows},${numFileDownloadFlowsInEvents},${computationTime}" >>$OUTPUT
    # update counter
    s=$(( ++s ))
done < "$INPUT" 

echo "done."
echo "Total ${s} line processed and wrote to $OUTPUT csv file."
