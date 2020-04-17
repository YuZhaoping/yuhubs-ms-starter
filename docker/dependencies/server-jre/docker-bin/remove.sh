#!/usr/bin/env bash

docker image rm -f com.yuhubs.ms/server-jre:8u251 && \
docker image prune -f
