#to run with mvn
#cd "/home/muffin/Documents/Universiteit/master internship/ArchChangeTracker/ParentProject"

#mvn exec:java -pl ArchChangeTracker -Dexec.args="'-if' '/home/muffin/Documents/Universiteit/master internship/mina/mina_arcan_trunk' '-of' '/home/muffin/Documents/Universiteit/master internship/mina/analysis/mina_testrun.csv' '-name' '[mina]'"


#to run jar
cd "/home/muffin/Documents/Universiteit/master internship/"

java -jar ArchChangeTracker.jar "-if" "/home/muffin/Documents/Universiteit/master internship/mina/mina_arcan_trunk" "-of" "/home/muffin/Documents/Universiteit/master internship/mina/analysis/mina_testrun_jar.csv" "-name" "[mina]"


