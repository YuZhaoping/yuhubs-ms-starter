#!/usr/bin/env bash

cd $(dirname $0) && cd ../

docker build -t com.yuhubs.ms/auth-server:0.0.1 .
