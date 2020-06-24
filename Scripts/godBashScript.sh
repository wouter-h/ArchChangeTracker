cd "/home/muffin/Documents/Universiteit/master internship/bash script"

#./masterBranchCommitFinder.sh "/home/muffin/Documents/Universiteit/master internship/zookeeper/zookeeper" "/home/muffin/Documents/Universiteit/master internship/zookeeper/zookeeper_arcan" "/home/muffin/Documents/Universiteit/master internship/zookeeper/zookeeper_arcan_master" "master"

#./gapFinder.sh "/home/muffin/Documents/Universiteit/master internship/pdfbox/pdfbox" "/home/muffin/Documents/Universiteit/master internship/pdfbox/pdfbox_trunk"

#./tagExtractor.sh "/home/muffin/Documents/Universiteit/master internship/pdfbox/pdfbox" "/home/muffin/Documents/Universiteit/master internship/pdfbox/pdfbox_trunk"

#./CommitDateLinker.sh "/home/muffin/Documents/Universiteit/master internship/pdfbox/pdfbox" "/home/muffin/Documents/Universiteit/master internship/pdfbox/pdfbox_trunk"

cd "/home/muffin/Documents/Universiteit/master internship/ArchChangeTracker/ParentProject"

mvn exec:java -pl SequentialRunner -Dexec.args="'-if' '/home/muffin/Documents/Universiteit/master internship/pdfbox/pdfbox_trunk' '-of' '/home/muffin/Documents/Universiteit/master internship/pdfbox/analysis/pdfbox_analysis_nameFilter.csv' '-name' '[pdfbox,fontbox,jempbox]' '/home/muffin/Documents/Universiteit/master internship/pdfbox/analysis/pdfbox_analysis_nameFilter.csv' '/home/muffin/Documents/Universiteit/master internship/bash script/titles' '/home/muffin/Documents/Universiteit/master internship/pdfbox/analysis/pdfbox_analysis_full_nameFilter.csv' '---+---+---' 'PDFBOX-' '#' '/home/muffin/Documents/Universiteit/master internship/bash script/gapCount' '/home/muffin/Documents/Universiteit/master internship/bash script/commitDates'"
