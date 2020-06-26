cd "/home/muffin/Documents/Universiteit/master internship/bash script"

./masterBranchCommitFinder.sh "/home/muffin/Documents/Universiteit/master internship/sqoop/sqoop" "/home/muffin/Documents/Universiteit/master internship/sqoop/sqoop_arcan" "/home/muffin/Documents/Universiteit/master internship/sqoop/sqoop_arcan_trunk" "trunk"

./gapFinder.sh "/home/muffin/Documents/Universiteit/master internship/sqoop/sqoop" "/home/muffin/Documents/Universiteit/master internship/sqoop/sqoop_arcan_trunk" "/home/muffin/Documents/Universiteit/master internship/sqoop/analysis/gaps"

./tagExtractor.sh "/home/muffin/Documents/Universiteit/master internship/sqoop/sqoop" "/home/muffin/Documents/Universiteit/master internship/sqoop/sqoop_arcan_trunk" "/home/muffin/Documents/Universiteit/master internship/sqoop/analysis/titles"

./CommitDateLinker.sh "/home/muffin/Documents/Universiteit/master internship/sqoop/sqoop" "/home/muffin/Documents/Universiteit/master internship/sqoop/sqoop_arcan_trunk" "/home/muffin/Documents/Universiteit/master internship/sqoop/analysis/dates"

cd "/home/muffin/Documents/Universiteit/master internship"

java -jar ArchChangeTracker_Submodule_SequentialRunner.jar "-if" "/home/muffin/Documents/Universiteit/master internship/sqoop/sqoop_arcan_trunk" "-of" "/home/muffin/Documents/Universiteit/master internship/sqoop/analysis/sqoop_analysisv2.csv" "-name" "sqoop" "-offset" 1 "/home/muffin/Documents/Universiteit/master internship/sqoop/analysis/sqoop_analysisv2.csv" "/home/muffin/Documents/Universiteit/master internship/sqoop/analysis/titles" "/home/muffin/Documents/Universiteit/master internship/sqoop/analysis/sqoop_analysisv2_full.csv" "---+---+---" "SQOOP-" "#" "/home/muffin/Documents/Universiteit/master internship/sqoop/analysis/gaps" "/home/muffin/Documents/Universiteit/master internship/sqoop/analysis/dates"
