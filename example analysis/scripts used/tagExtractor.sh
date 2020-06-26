#!/bin/bash

if [ $# -ne 3 ]
  then
    echo "Usage ./tagExtractor.sh <location of the folder where the git directory is in (does not include .git itself)> <graphml dir location> <output_file_location>\n"
fi

gitDir=$1
graphmlDir=$2
outputFileLocation=$3
currentDir=$PWD

result=""

cd "$gitDir"

{
	read
	while read line; do
		line1=${line:0:-8}
		commit=${line1: -40}
		echo "$commit"
		temp=$(git log --format=%B -n 1 "$commit")
		if [ -z "$result" ]; then
			result="$commit $temp"
		else
			result="$result
---+---+---$commit $temp"
		fi
	done
} < <(find "$graphmlDir" -maxdepth 1 | sort -V)

cd "$currentDir"

touch "$outputFileLocation"

printf "%s" "$result" > "$outputFileLocation"
