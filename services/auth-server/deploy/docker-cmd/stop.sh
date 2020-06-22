#!/usr/bin/env bash

docker stop ecom-auth-server && \
docker container rm -f ecom-auth-server
