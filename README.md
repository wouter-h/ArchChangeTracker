**ArchChangeTracker**<br/>
Program right now:<br/>
The program has been compiled with Java 11 and maven 3.6.<br/>
In order to build the modules move into the ParentProject directory and do: mvn clean install<br/>
After which one of the following commands can be run:<br/>
<br/>
To run just the ArchChangeTracker submodule: <br/>
-if: stands for input file<br/>
followed by an input file location.<br/>
-of: stands for output file<br/>
followed by an output file location.<br/>
mvn exec:java -pl ArchChangeTracker -Dexec.args="-if '/home/muffin/Documents/Universiteit/master internship/tajo_arcan' -of '/home/muffin/Documents/Universiteit/master internship/csvfiles/1.csv'"
<br/>
<br/>
first argument: Commits fed to the bash script.<br/>
second argument: the results of the bash script.<br/>
third argument: the output file location that will be used by the program.<br/>
fourth argument: delimeter used by the bash script to seperate commit info.<br/>
fifth argument: issue identifier token used to find the start of an issue.<br/>
sixth argument: start of a pull request number.<br/>
To run just the PRNumberAndIssueIdFinder submodule: <br/>
mvn exec:java -pl PRNumberAndIssueIdFinder -Dexec.args="'/home/muffin/Documents/Universiteit/master internship/commits a2a/Commits.csv' '/home/muffin/Documents/Universiteit/master internship/bash script/titles' '/home/muffin/Documents/Universiteit/master internship/commit issue pr number/output.csv' '---+---+---' 'TAJO-' '#'"
<br/>
<br/>
The commands combined of the ArchChangeTracker submodule together with the PRNumberAndIssueIdFinder submodule.<br/>
To run just the SequentialRunner submodule (runs the ArchChangeTracker and PRNumberAndIssueIdFinder sequential): <br/>
mvn exec:java -pl SequentialRunner -Dexec.args="'-if' '/home/muffin/Documents/Universiteit/master internship/tajo_arcan/tajo_arcan' '-of' '/home/muffin/Documents/Universiteit/master internship/csvfiles/1.csv' '/home/muffin/Documents/Universiteit/master internship/commits a2a/Commits.csv' '/home/muffin/Documents/Universiteit/master internship/bash script/titles' '/home/muffin/Documents/Universiteit/master internship/commit issue pr number/output.csv' '---+---+---' 'TAJO-' '#'"
