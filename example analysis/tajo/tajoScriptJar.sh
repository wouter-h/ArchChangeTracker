cd "/home/muffin/Documents/Universiteit/master internship/bash script"

./masterBranchCommitFinder.sh "/home/muffin/Documents/Universiteit/master internship/tajo/tajo" "/home/muffin/Documents/Universiteit/master internship/tajo/tajo_arcan" "/home/muffin/Documents/Universiteit/master internship/tajo/tajo_arcan_master" "master"

./gapFinder.sh "/home/muffin/Documents/Universiteit/master internship/tajo/tajo" "/home/muffin/Documents/Universiteit/master internship/tajo/tajo_arcan_master" "/home/muffin/Documents/Universiteit/master internship/tajo/analysis/gaps"

./tagExtractor.sh "/home/muffin/Documents/Universiteit/master internship/tajo/tajo" "/home/muffin/Documents/Universiteit/master internship/tajo/tajo_arcan_master" "/home/muffin/Documents/Universiteit/master internship/tajo/analysis/titles"

./CommitDateLinker.sh "/home/muffin/Documents/Universiteit/master internship/tajo/tajo" "/home/muffin/Documents/Universiteit/master internship/tajo/tajo_arcan_master" "/home/muffin/Documents/Universiteit/master internship/tajo/analysis/dates"

cd "/home/muffin/Documents/Universiteit/master internship"

java -jar ArchChangeTracker_Submodule_SequentialRunner.jar "-if" "/home/muffin/Documents/Universiteit/master internship/tajo/tajo_arcan_master" "-of" "/home/muffin/Documents/Universiteit/master internship/tajo/analysis/tajo_analysisv2.csv" "-name" "[nta,tajo]" "-offset" 1 "/home/muffin/Documents/Universiteit/master internship/tajo/analysis/tajo_analysisv2.csv" "/home/muffin/Documents/Universiteit/master internship/tajo/analysis/titles" "/home/muffin/Documents/Universiteit/master internship/tajo/analysis/tajo_analysisv2_full.csv" "---+---+---" "TAJO-" "#" "/home/muffin/Documents/Universiteit/master internship/tajo/analysis/gaps" "/home/muffin/Documents/Universiteit/master internship/tajo/analysis/dates"
