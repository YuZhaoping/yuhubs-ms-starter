#!/usr/bin/env bash

cd $(dirname $0)

port=6379

docker start redis-${port}
