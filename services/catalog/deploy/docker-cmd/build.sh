#!/usr/bin/env bash

cd $(dirname $0) && cd ../

docker build -t com.yuhubs.ecom/catalog-service:0.0.1 .
