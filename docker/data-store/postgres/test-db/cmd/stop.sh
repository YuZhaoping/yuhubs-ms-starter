#!/usr/bin/env bash

docker stop test-pg-db && \
docker container rm -f test-pg-db
