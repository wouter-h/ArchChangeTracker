#!/bin/bash

if [ $# -ne 3 ]
  then
    echo "Usage ./CommitDateLinker.sh <location of the folder where the git directory is in (does not include .git itself)> <graphml dir location> <output_file_location>\n"
fi

gitDir=$1
graphmlDir=$2
outputFileLoc=$3
currentDir=$PWD

result=""

cd "$gitDir"

{
	read
	while read line; do
		line1=${line:0:-8}
		commit=${line1: -40}
		echo "$commit"
		temp=$(git show --no-patch --no-notes --pretty='%cI' "$commit")
		date=${temp:0:10}
		echo "$date $cond"
		if [ -z "$result" ]; then
			result="$commit,$date"
		else
			result="$result
$commit,$date"
		fi
	done
} < <(find "$graphmlDir" -maxdepth 1 | sort -V)

cd "$currentDir"

touch "$outputFileLoc"

printf "%s" "$result" > "$outputFileLoc"
