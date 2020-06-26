cd "/home/muffin/Documents/Universiteit/master internship/bash script"

./masterBranchCommitFinder.sh "/home/muffin/Documents/Universiteit/master internship/mina/mina" "/home/muffin/Documents/Universiteit/master internship/mina/mina_arcan" "/home/muffin/Documents/Universiteit/master internship/mina/mina_arcan_trunk" "trunk"

./gapFinder.sh "/home/muffin/Documents/Universiteit/master internship/mina/mina" "/home/muffin/Documents/Universiteit/master internship/mina/mina_arcan_trunk" "/home/muffin/Documents/Universiteit/master internship/mina/analysis/gaps"

./tagExtractor.sh "/home/muffin/Documents/Universiteit/master internship/mina/mina" "/home/muffin/Documents/Universiteit/master internship/mina/mina_arcan_trunk" "/home/muffin/Documents/Universiteit/master internship/mina/analysis/titles"

./CommitDateLinker.sh "/home/muffin/Documents/Universiteit/master internship/mina/mina" "/home/muffin/Documents/Universiteit/master internship/mina/mina_arcan_trunk" "/home/muffin/Documents/Universiteit/master internship/mina/analysis/dates"

cd "/home/muffin/Documents/Universiteit/master internship"

java -jar ArchChangeTracker_Submodule_SequentialRunner.jar "-if" "/home/muffin/Documents/Universiteit/master internship/mina/mina_arcan_trunk" "-of" "/home/muffin/Documents/Universiteit/master internship/mina/analysis/mina_analysisv2.csv" "-name" "mina" "-offset" 1 "/home/muffin/Documents/Universiteit/master internship/mina/analysis/mina_analysisv2.csv" "/home/muffin/Documents/Universiteit/master internship/mina/analysis/titles" "/home/muffin/Documents/Universiteit/master internship/mina/analysis/mina_analysisv2_full.csv" "---+---+---" "MINA-" "#" "/home/muffin/Documents/Universiteit/master internship/mina/analysis/gaps" "/home/muffin/Documents/Universiteit/master internship/mina/analysis/dates"
