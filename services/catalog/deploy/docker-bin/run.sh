#!/usr/bin/env bash

docker run -d --name ecom-catalog-service \
  --network=ecom-backend --net-alias=catalog-service -p 8082:8080 \
  com.yuhubs.ecom/catalog-service:0.0.1
