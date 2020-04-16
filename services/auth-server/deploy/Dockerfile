FROM com.yuhubs.ms/server-jre:8u241


ENV MS_HOME="/opt/yuhubs/ms" \
    MS_VAR_DIR="/opt/yuhubs/ms/var" \
    APP_HOME="/opt/yuhubs/ms/auth"

RUN set -x \
    && mkdir -p $MS_VAR_DIR && chmod 777 $MS_VAR_DIR \
    && addgroup -g 1010 -S yuhubs \
    && adduser -S -D -H -u 1010 -h $APP_HOME -s /sbin/nologin -G yuhubs -g yuhubs yuhubs \
    && mkdir -p $APP_HOME \
    && chown -R yuhubs:yuhubs $APP_HOME

USER yuhubs


VOLUME ["${APP_HOME}", "${MS_VAR_DIR}"]

WORKDIR $APP_HOME


ENV APP_NAME="auth-server" \
    APP_VERSION="0.0.1-SNAPSHOT"

COPY ./build/$APP_NAME-$APP_VERSION.jar $APP_HOME/app.jar

COPY ./docker-entrypoint.sh $APP_HOME/
ENTRYPOINT ["./docker-entrypoint.sh"]


EXPOSE 8080
CMD ["start-service"]
