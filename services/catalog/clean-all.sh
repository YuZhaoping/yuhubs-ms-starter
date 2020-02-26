#!/usr/bin/env bash

cd $(dirname $0)

mvn clean && rm -f deploy/build/*.war
