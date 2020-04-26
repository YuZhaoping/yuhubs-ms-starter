#!/usr/bin/env bash

cd $(dirname $0)

for port in `seq 7000 7005`; do \
  docker start redis-${port};
done
