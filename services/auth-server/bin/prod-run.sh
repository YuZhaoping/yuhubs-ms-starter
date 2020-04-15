#!/usr/bin/env bash

cd $(dirname $0) && cd ../

APP_NAME="auth-server"
APP_PROD_JAR="deploy/build/$APP_NAME-*.jar"

if ls $APP_PROD_JAR 1> /dev/null 2>&1; then
  java -jar $APP_PROD_JAR
else
  mvn clean && mvn package -DskipTests && \
  java -jar $APP_PROD_JAR
fi
