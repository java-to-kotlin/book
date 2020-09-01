#!/bin/sh
set -e

./gradlew --warning-mode=all clean code:test build
