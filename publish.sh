#!/usr/bin/env bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
cd ${DIR}

rsync -avze ssh out/pdf/book.pdf dmcg@ssh.oneeyedmen.com:oneeyedmen_com/book
