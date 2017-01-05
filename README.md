# analytics

There are 2 class files with main methods: bRelevantCollFilter and bRelevantTryScore

The bRelevantTryScore takes the following commandline arugments:
InputFilePath
OutputFileName
username
cutOffDate (in form MM/DD/YYYY)

The bRelevantCollFilter takes the above arguments (same order) plus:
NumberOfIterations (to train the model)
Lambda (to fit the model)
