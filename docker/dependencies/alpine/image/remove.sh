#!/usr/bin/env bash

docker image rm -f alpine:3.11 && \
docker image prune -f
