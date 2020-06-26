cd "/home/muffin/Documents/Universiteit/master internship/bash script"

./masterBranchCommitFinder.sh "/home/muffin/Documents/Universiteit/master internship/zookeeper/zookeeper" "/home/muffin/Documents/Universiteit/master internship/zookeeper/zookeeper_arcan" "/home/muffin/Documents/Universiteit/master internship/zookeeper/zookeeper_arcan_master" "master"

./gapFinder.sh "/home/muffin/Documents/Universiteit/master internship/zookeeper/zookeeper" "/home/muffin/Documents/Universiteit/master internship/zookeeper/zookeeper_arcan_master" "/home/muffin/Documents/Universiteit/master internship/zookeeper/analysis/gaps"

./tagExtractor.sh "/home/muffin/Documents/Universiteit/master internship/zookeeper/zookeeper" "/home/muffin/Documents/Universiteit/master internship/zookeeper/zookeeper_arcan_master" "/home/muffin/Documents/Universiteit/master internship/zookeeper/analysis/titles"

./CommitDateLinker.sh "/home/muffin/Documents/Universiteit/master internship/zookeeper/zookeeper" "/home/muffin/Documents/Universiteit/master internship/zookeeper/zookeeper_arcan_master" "/home/muffin/Documents/Universiteit/master internship/zookeeper/analysis/dates"

cd "/home/muffin/Documents/Universiteit/master internship"

java -jar ArchChangeTracker_Submodule_SequentialRunner.jar "-if" "/home/muffin/Documents/Universiteit/master internship/zookeeper/zookeeper_arcan_master" "-of" "/home/muffin/Documents/Universiteit/master internship/zookeeper/analysis/zookeeper_analysisv2.csv" "-name" "zookeeper" "-offset" 1 "/home/muffin/Documents/Universiteit/master internship/zookeeper/analysis/zookeeper_analysisv2.csv" "/home/muffin/Documents/Universiteit/master internship/zookeeper/analysis/titles" "/home/muffin/Documents/Universiteit/master internship/zookeeper/analysis/zookeeper_analysisv2_full.csv" "---+---+---" "ZOOKEEPER-" "#" "/home/muffin/Documents/Universiteit/master internship/zookeeper/analysis/gaps" "/home/muffin/Documents/Universiteit/master internship/zookeeper/analysis/dates"
