#!/usr/bin/env bash

cd $(dirname $0) && cd ../

mvn clean && rm -f deploy/build/*.war && \
mvn package -DskipTests
