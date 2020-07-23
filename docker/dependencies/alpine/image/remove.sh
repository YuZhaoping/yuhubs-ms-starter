#!/usr/bin/env bash

docker image rm -f alpine:3.12 && \
docker image prune -f
