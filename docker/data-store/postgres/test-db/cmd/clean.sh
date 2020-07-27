#!/usr/bin/env bash

docker image rm -f com.yuhubs.ms/test-pg:latest && \
docker image prune -f
