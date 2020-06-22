#!/usr/bin/env bash

docker image rm -f com.yuhubs.ecom/front-end:0.0.1 && \
docker volume prune -f
