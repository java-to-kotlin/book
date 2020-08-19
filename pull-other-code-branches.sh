#!/bin/sh
set -e

cd ../refactoring-to-kotlin-code

current_branch=`git rev-parse --abbrev-ref HEAD`
echo Currently on $current_branch

branches=(
  baseline
  builders
  data-and-extensions
  effects
  errors
  json-dsl
  master
  mutation-to-transformation
  nullability
  value_types_currency_conversion
)

git fetch
for branch in "${branches[@]/$CURRENT_BRANCH}"
do
	git checkout $branch
	git pull --force
done

git checkout CURRENT_BRANCH