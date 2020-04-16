#!/usr/bin/env bash

docker image rm -f nginx:alpine && \
docker image prune -f
