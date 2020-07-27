#!/usr/bin/env bash

docker image rm -f postgres:alpine && \
docker image prune -f
