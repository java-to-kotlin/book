#!/bin/bash

git-filter-repo --force \
--source ../refactoring-to-kotlin-code \
--target ../refactoring-to-kotlin-code \
--message-callback '
renames = {}
with open("renames.tsv") as f:
  for line in f:
    key, value = line.split()
    renames[key] = value

messageString = message.decode("UTF-8")
marker = None if not ":" in messageString else messageString.split(":")[0].strip()

rename = messageString.replace(marker, renames[marker], 1) if marker and marker in renames else None

return message if not rename else rename.encode("UTF-8")
'

