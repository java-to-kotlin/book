#!/usr/bin/env bash
set -e

~/Documents/Projects/book/build/install/book/bin/book src/test/kotlin/chapter1 build/chapter1.md
cp build/chapter1.md ~/Documents/Projects/website-jekyll/site/_posts/2019-03-29-chapter1.md