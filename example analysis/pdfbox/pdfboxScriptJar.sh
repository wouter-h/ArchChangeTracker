cd "/home/muffin/Documents/Universiteit/master internship/bash script"

./masterBranchCommitFinder.sh "/home/muffin/Documents/Universiteit/master internship/pdfbox/pdfbox" "/home/muffin/Documents/Universiteit/master internship/pdfbox/pdfbox_arcan" "/home/muffin/Documents/Universiteit/master internship/pdfbox/pdfbox_arcan_trunk" "trunk"

./gapFinder.sh "/home/muffin/Documents/Universiteit/master internship/pdfbox/pdfbox" "/home/muffin/Documents/Universiteit/master internship/pdfbox/pdfbox_arcan_trunk" "/home/muffin/Documents/Universiteit/master internship/pdfbox/analysis/gaps"

./tagExtractor.sh "/home/muffin/Documents/Universiteit/master internship/pdfbox/pdfbox" "/home/muffin/Documents/Universiteit/master internship/pdfbox/pdfbox_arcan_trunk" "/home/muffin/Documents/Universiteit/master internship/pdfbox/analysis/titles"

./CommitDateLinker.sh "/home/muffin/Documents/Universiteit/master internship/pdfbox/pdfbox" "/home/muffin/Documents/Universiteit/master internship/pdfbox/pdfbox_arcan_trunk" "/home/muffin/Documents/Universiteit/master internship/pdfbox/analysis/dates"

cd "/home/muffin/Documents/Universiteit/master internship"

java -jar ArchChangeTracker_Submodule_SequentialRunner.jar "-if" "/home/muffin/Documents/Universiteit/master internship/pdfbox/pdfbox_arcan_trunk" "-of" "/home/muffin/Documents/Universiteit/master internship/pdfbox/analysis/pdfbox_analysisv2.csv" "-name" "[pdfbox,fontbox,jempbox]" "-offset" 1 "/home/muffin/Documents/Universiteit/master internship/pdfbox/analysis/pdfbox_analysisv2.csv" "/home/muffin/Documents/Universiteit/master internship/pdfbox/analysis/titles" "/home/muffin/Documents/Universiteit/master internship/pdfbox/analysis/pdfbox_analysisv2_full.csv" "---+---+---" "PDFBOX-" "#" "/home/muffin/Documents/Universiteit/master internship/pdfbox/analysis/gaps" "/home/muffin/Documents/Universiteit/master internship/pdfbox/analysis/dates"
