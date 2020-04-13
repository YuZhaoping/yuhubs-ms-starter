#!/usr/bin/env bash

cd $(dirname $0) && cd ../

mvn clean && mvn -Pdev package -DskipTests
