#!/usr/bin/env bash

cd $(dirname $0) && cd ../


export YUHUBS_MS_HTML_LOCATION=`../../front-end/bin/export-html-location.sh`
echo ""
echo "export YUHUBS_MS_HTML_LOCATION=\"${YUHUBS_MS_HTML_LOCATION}\""
echo ""


APP_NAME="catalog-service"
APP_DEV_JAR="deploy/build/dev/$APP_NAME-*.war"


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


if ls $APP_DEV_JAR 1> /dev/null 2>&1; then
  java -jar $APP_DEV_JAR $APP_ARGS
else
  mvn clean && mvn -Pdev package -DskipTests && mvn clean && \
  java -jar $APP_DEV_JAR $APP_ARGS
fi
