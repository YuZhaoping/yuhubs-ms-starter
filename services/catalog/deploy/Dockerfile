FROM com.yuhubs.ms/server-jre:8u251


ENV MS_HOME="/opt/yuhubs/ecom" \
    MS_VAR_DIR="/opt/yuhubs/ecom/var" \
    APP_HOME="/opt/yuhubs/ecom/catalog"

RUN set -x \
    && mkdir -p $MS_VAR_DIR && chmod 777 $MS_VAR_DIR \
    && addgroup -g 1010 -S yuhubs \
    && adduser -S -D -H -u 1010 -h $APP_HOME -s /sbin/nologin -G yuhubs -g yuhubs yuhubs \
    && mkdir -p $APP_HOME \
    && chown -R yuhubs:yuhubs $APP_HOME

USER yuhubs


VOLUME ["${APP_HOME}", "${MS_VAR_DIR}"]

WORKDIR $APP_HOME


ENV APP_NAME="catalog-service" \
    APP_VERSION="0.0.1-SNAPSHOT"

COPY ./build/$APP_NAME-$APP_VERSION.war $APP_HOME/app.war

COPY ./docker-entrypoint.sh $APP_HOME/
ENTRYPOINT ["./docker-entrypoint.sh"]


EXPOSE 8080
CMD ["start-service"]
