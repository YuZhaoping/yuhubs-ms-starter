#!/usr/bin/env bash

cd $(dirname $0) && cd ../

export YUHUBS_MS_HTML_LOCATION=`../../front-end/bin/export-html-location.sh`
echo ""
echo "export YUHUBS_MS_HTML_LOCATION=\"${YUHUBS_MS_HTML_LOCATION}\""
echo ""


APP_NAME="catalog-service"
APP_DEV_JAR="deploy/build/dev/$APP_NAME-*.war"

if ls $APP_DEV_JAR 1> /dev/null 2>&1; then
  java -jar $APP_DEV_JAR
else
  mvn clean && mvn -Pdev package -DskipTests && \
  java -jar $APP_DEV_JAR
fi
