#!/usr/bin/env bash

docker run -d --name ecom-auth-server \
  --network=ecom-backend --net-alias=auth-server -p 8081:8080 \
  com.yuhubs.ms/auth-server:0.0.1
