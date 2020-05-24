#!/bin/sh
set -e

if [ "$1" = "--fast" ]
then
  shift
else
  if [ "$1" = "clean" ]
  then
    clean=clean
  else
    clean=
  fi

  # test the code
  ./gradlew --warning-mode=all $clean code:test

  # include the code in the Asciidoc
  ./gradlew --warning-mode=all build
fi

# build the book
docker run --rm --tty -v "$PWD"/:/documents/ asciidoctor/docker-asciidoctor make $clean "$@"
op