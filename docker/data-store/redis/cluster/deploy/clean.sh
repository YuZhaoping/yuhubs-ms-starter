#!/usr/bin/env bash

cd $(dirname $0)


for port in `seq 7000 7005`; do \
  docker container rm -f redis-${port};
done


for port in `seq 7000 7005`; do \
  rm -rf ./${port}; \
done
