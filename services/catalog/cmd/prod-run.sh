#!/usr/bin/env bash

cd $(dirname $0) && cd ../


APP_NAME="catalog-service"
APP_PROD_JAR="deploy/build/$APP_NAME-*.war"


APP_ARGS=

while (( "$#" )); do
  case "$1" in
    --ssl)
      APP_ARGS="$APP_ARGS --spring.config.additional-location=classpath:server-ssl.properties"
      shift
      ;;
    *)
      shift
      ;;
  esac
done


if ls $APP_PROD_JAR 1> /dev/null 2>&1; then
  java -jar $APP_PROD_JAR $APP_ARGS
else
  mvn clean && mvn package -DskipTests && mvn clean && \
  java -jar $APP_PROD_JAR $APP_ARGS
fi
