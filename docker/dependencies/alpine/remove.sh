#!/usr/bin/env bash

docker image rm -f alpine:3.10 && \
docker image prune -f
