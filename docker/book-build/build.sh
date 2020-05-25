#!/bin/sh
set -e
docker build --tag="$(basename "$(dirname $0)")" "$(dirname $0)"
