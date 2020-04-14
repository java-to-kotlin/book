#!/bin/sh
set -e

if [ "$1" = "--fast" ]
then
  shift
else
  # test the code
  ./gradlew code:test
  (cd ../refactoring-to-kotlin-code && ./gradlew test)

  # include the code in the Asciidoc
  ./gradlew build
fi

# build the book
docker run --rm --tty -v "$PWD"/:/documents/ asciidoctor/docker-asciidoctor make "$@"
