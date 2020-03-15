#!/bin/sh
set -e
image=asciidoctor/docker-asciidoctor
docker run --rm --tty -v "$PWD"/:/documents/ $image make "$@"
