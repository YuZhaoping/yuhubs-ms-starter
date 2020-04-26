#!/usr/bin/env bash

cd $(dirname $0)

port=6379

docker exec -it redis-${port} sh -c "redis-cli -p ${port}"
