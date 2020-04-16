#!/usr/bin/env bash

docker stop ecom-catalog-service && \
docker container rm -f ecom-catalog-service
