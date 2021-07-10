#!/bin/bash

# Once this is done
# Delete the attic branches by hand
# git remote add origin git@github.com:refactoring-to/kotlin-code-public.git
# git push origin --all
# git push origin --tags

git-filter-repo --force \
--source ../refactoring-to-kotlin-code \
--target ../public-repo \
--blob-callback '
import os
import builtins

def stripped(line):
    if line.strip().startswith("///"):
        return None
    elif line.find("///") != -1:
        return line[:line.find("///")]
    else:
        return line

def process(lines):
    return builtins.filter(lambda x: x is not None, map(stripped, lines))

try:
    new_lines = process(blob.data.decode("UTF-8").splitlines())
    blob.data = "\n".join(new_lines).encode("UTF-8")
    print("success")
except:
    print("Error:", sys.exc_info()[0])
    # One file doesnt decode, dont know which
'

