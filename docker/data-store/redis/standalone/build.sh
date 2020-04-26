#!/usr/bin/env bash

cd $(dirname $0)

port=6379

docker run -d -p ${port}:${port} --name redis-${port} redis:alpine
