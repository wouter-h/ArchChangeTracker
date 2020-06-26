#!/bin/bash
#SBATCH --time=10:00:00
#SBATCH --nodes=1
#SBATCH --mem=30GB
#SBATCH --job-name=cassandra_archtracker_analysis
#SBATCH --mail-type=ALL
#SBATCH --mail-user=w.hertsenberg@student.rug.nl
#SBATCH --output=job-%j.log

module load Java/11.0.2
module load git/2.16.1-foss-2018a

./gapFinder.sh "/data/s2992795/cassandra/cassandra_git/cassandra" "/data/s2992795/cassandra/cassandra_trunk" "/data/s2992795/cassandra/analysis/gaps"

./tagExtractor.sh "/data/s2992795/cassandra/cassandra_git/cassandra" "/data/s2992795/cassandra/cassandra_trunk" "/data/s2992795/cassandra/analysis/titles"

./CommitDateLinker.sh "/data/s2992795/cassandra/cassandra_git/cassandra" "/data/s2992795/cassandra/cassandra_trunk" "/data/s2992795/cassandra/analysis/dates"

java -jar ArchChangeTracker_Submodule_SequentialRunner.jar "-if" "/data/s2992795/cassandra/cassandra_trunk" "-of" "/data/s2992795/cassandra/analysis/cassandra_analysisv2.csv" "-name" "cassandra" "-offset" 1 "/data/s2992795/cassandra/analysis/cassandra_analysisv2.csv" "/data/s2992795/cassandra/analysis/titles" "/data/s2992795/cassandra/analysis/cassandra_analysis_fullv2.csv" "---+---+---" "CASSANDRA-" "#" "/data/s2992795/cassandra/analysis/gaps" "/data/s2992795/cassandra/analysis/dates"
