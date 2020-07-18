#!/usr/bin/env bash

cd $(dirname $0)


mvn -N install

mvn install && mvn clean
