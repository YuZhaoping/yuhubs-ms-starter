#!/usr/bin/env bash

cd $(dirname $0) && cd ../

docker build -t com.yuhubs.ms/server-jre:8u251 .
