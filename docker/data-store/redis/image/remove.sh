#!/usr/bin/env bash

docker image rm -f redis:alpine && \
docker image prune -f
