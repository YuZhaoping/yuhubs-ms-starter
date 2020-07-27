#!/usr/bin/env bash

docker run -d --name test-pg-db \
  --network=ecom-backend --net-alias=test-pg-db -p 5432:5432 \
  -e POSTGRES_PASSWORD=u123@Yuhubs -v test-pg-vol:/var/lib/postgresql/data \
  com.yuhubs.ms/test-pg:latest
