#!/usr/bin/env bash

cd $(dirname $0)

for port in `seq 7000 7005`; do \
  mkdir -p ./deploy/${port}/conf \
  && PORT=${port} envsubst < ./node-conf.tmpl > ./deploy/${port}/conf/redis.conf \
  && mkdir -p ./deploy/${port}/data; \
done
