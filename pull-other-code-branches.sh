#!/bin/bash
set -e

cd ../refactoring-to-kotlin-code

current_branch=$(git rev-parse --abbrev-ref HEAD)
echo Currently on "$current_branch"

remote_branches=$(git ls-remote --heads origin | cut -f 2 | cut -d / -f 3 | sort)
local_branches=$(git branch --list | cut -c 3- | sort)
local_only_branches=$(comm -23 <(echo "$local_branches") <(echo "$remote_branches"))

git fetch

for branch in $remote_branches
do
  if [ "$branch" != "$current_branch" ]
  then
    git branch -f "$branch" origin/"$branch"
  else
    echo "Branch '$current_branch' not changed because it is the current branch"
  fi
done

if [ -z "$local_only_branches" ]
then
  echo "All your local branches are in origin"
else
  echo "The following local branches are not in origin and are either new or obsolete:"
  for b in $local_only_branches
  do
    echo "* $b"
  done
fi
