#!/bin/bash
set -e

cd ../refactoring-to-kotlin-code

current_branch=$(git rev-parse --abbrev-ref HEAD)
echo Currently on "$current_branch"

branches=$(git ls-remote --heads origin | cut -f 2 | xargs basename)

git fetch

for branch in $branches
do
  if [ "$branch" != "$current_branch" ]
  then
    git branch -f "$branch" origin/"$branch"
  else
    echo "not changing current branch $current_branch"
  fi
done
