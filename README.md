**ArchChangeTracker**<br/>
Program right now:<br/>
reads in csv data from structure101<br/>
creates a *tinkerpop.gremlin.structure.Graph* from the graphml data from arcan<br/>
compares the csv data from structure101 to the data in graphml from arcan and outputs the accuracy per package and the total accuracy. It right now only compares package depenendencies and not any class dependencies.<br/>
<br/>
summarized data:<br/>
only package dependencies: total right: 178 total compared: 385<br/>
if a class can be afferent of a package (and if a class can be depended on another class (returns 0 right)): total right: 215 total compared: 385

