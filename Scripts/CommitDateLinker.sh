#!/bin/bash

if [ $# -lt 3 ]
  then
    echo "Usage ./CommitDateLinker.sh <location of the folder where the git directory is in (does not include .git itself)> <graphml dir location>\n"
fi

gitDir=$1
graphmlDir=$2
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

touch commitDates

printf "%s" "$result" > commitDates
