#!/usr/bin/env bash

cd $(dirname $0)

mvn clean && mvn -Pdev package -DskipTests
