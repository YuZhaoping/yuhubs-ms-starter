#!/usr/bin/env bash

cd $(dirname $0)

docker exec -it redis-7000 sh -c "redis-cli -c -p 7000"
