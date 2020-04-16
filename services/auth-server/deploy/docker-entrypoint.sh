#!/bin/sh
set -eo pipefail

export MS_HOME=/opt/yuhubs/ms
export MS_VAR_DIR=$MS_HOME/var
export APP_HOME=$MS_HOME/auth
export APP_VAR_DIR=$MS_VAR_DIR/auth
export APP_LOG_DIR=$APP_VAR_DIR/log
export APP_TMP_DIR=$APP_VAR_DIR/tmp


[ ! -d "$APP_LOG_DIR" ] && mkdir -p $APP_LOG_DIR && chmod 777 $APP_LOG_DIR

[ ! -d "$APP_TMP_DIR" ] && mkdir -p $APP_TMP_DIR && chmod 777 $APP_TMP_DIR


APP_ARGS="--spring.profiles.active=docker"


if [ "${1:0:1}" = '-' ]; then
  set -- start-service "$@"
fi

if [ "$1" = 'start-service' ]; then
  exec java -jar ./app.jar $APP_ARGS "$@"
else
  exec "$@"
fi
