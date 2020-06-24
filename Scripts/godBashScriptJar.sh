cd "/home/muffin/Documents/Universiteit/master internship/bash script"

./gapFinder.sh "/home/muffin/Documents/Universiteit/master internship/zookeeper/zookeeper" "/home/muffin/Documents/Universiteit/master internship/zookeeper/zookeeper_arcan_master"

./tagExtractor.sh "/home/muffin/Documents/Universiteit/master internship/zookeeper/zookeeper" "/home/muffin/Documents/Universiteit/master internship/zookeeper/zookeeper_arcan_master"

./CommitDateLinker.sh "/home/muffin/Documents/Universiteit/master internship/zookeeper/zookeeper" "/home/muffin/Documents/Universiteit/master internship/zookeeper/zookeeper_arcan_master"

cd "/home/muffin/Documents/Universiteit/master internship"

java -jar ArchChangeTracker_Submodule_SequentialRunner.jar "-if" "/home/muffin/Documents/Universiteit/master internship/zookeeper/zookeeper_arcan_master" "-of" "/home/muffin/Documents/Universiteit/master internship/zookeeper/analysis/zookeeper_analysis.csv" "/home/muffin/Documents/Universiteit/master internship/zookeeper/analysis/zookeeper_analysis.csv" "/home/muffin/Documents/Universiteit/master internship/bash script/titles" "/home/muffin/Documents/Universiteit/master internship/zookeeper/analysis/zookeeper_analysis_full.csv" "---+---+---" "ZOOKEEPER-" "#" "/home/muffin/Documents/Universiteit/master internship/bash script/gapCount" "/home/muffin/Documents/Universiteit/master internship/bash script/commitDates"
