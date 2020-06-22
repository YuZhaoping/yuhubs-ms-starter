#!/usr/bin/env bash

docker image rm -f com.yuhubs.ecom/catalog-service:0.0.1 && \
docker volume prune -f
