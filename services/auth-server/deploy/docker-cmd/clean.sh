#!/usr/bin/env bash

docker image rm -f com.yuhubs.ms/auth-server:0.0.1 && \
docker volume prune -f
