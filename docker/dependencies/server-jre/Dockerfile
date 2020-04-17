# AlpineLinux with a glibc-2.31-r0 and Oracle Java 8
FROM alpine:3.11

MAINTAINER Zhaoping Yu <yuzhaoping1970@gmail.com>
# Thanks to Anastas Dancha <anapsix@random.io> for the original work

# Java version and other ENV
ENV JAVA_VERSION_MAJOR=8 \
    JAVA_VERSION_MINOR=241 \
    JAVA_HOME=/opt/jdk \
    PATH=/opt/jdk/bin:${PATH} \
    LD_LIBRARY_PATH=/usr/glibc-compat/lib:/usr/lib:/lib \
    LANG=C.UTF-8

COPY ./jdk/server-jre-${JAVA_VERSION_MAJOR}u${JAVA_VERSION_MINOR}-linux-x64.tar.gz /tmp/jdk.tar.gz

# Do all in one step
RUN set -ex && \
    apk -U upgrade && \
    apk add libstdc++ curl bash ca-certificates java-cacerts && \
    export GLIBC_RELEASE_URL="https://github.com/sgerrand/alpine-pkg-glibc/releases/download" && \
    export GLIBC_VERSION="2.31-r0" && \
    wget -q -O /etc/apk/keys/sgerrand.rsa.pub https://alpine-pkgs.sgerrand.com/sgerrand.rsa.pub && \
    for pkg in glibc-${GLIBC_VERSION} glibc-bin-${GLIBC_VERSION} glibc-i18n-${GLIBC_VERSION}; do \
        curl -sSL ${GLIBC_RELEASE_URL}/${GLIBC_VERSION}/${pkg}.apk -o /tmp/${pkg}.apk; done && \
    apk add /tmp/*.apk && \
    rm -v /tmp/*.apk && \
    ( /usr/glibc-compat/bin/localedef --force --inputfile POSIX --charmap UTF-8 C.UTF-8 > /dev/null 2>&1 || true ) && \
    echo "export LANG=C.UTF-8" > /etc/profile.d/locale.sh && \
    gunzip /tmp/jdk.tar.gz && \
    tar -C /opt -xf /tmp/jdk.tar && \
    ln -s ${JAVA_HOME}1.${JAVA_VERSION_MAJOR}.0_${JAVA_VERSION_MINOR} $JAVA_HOME && \
    find $JAVA_HOME/ -maxdepth 1 -mindepth 1 | grep -v jre | xargs rm -rf && \
    cd $JAVA_HOME/ && ln -s ./jre/bin ./bin && \
    sed -i s/#networkaddress.cache.ttl=-1/networkaddress.cache.ttl=10/ $JAVA_HOME/jre/lib/security/java.security && \
    apk del curl glibc-i18n && \
    find $JAVA_HOME/jre/bin -maxdepth 1 -mindepth 1 | grep -v java | xargs rm -rf && \
    rm -rf $JAVA_HOME/jre/lib/ext/nashorn.jar \
           $JAVA_HOME/jre/lib/oblique-fonts \
           /tmp/* \
           /var/cache/apk/* && \
    ln -sf /etc/ssl/certs/java/cacerts $JAVA_HOME/jre/lib/security/cacerts && \
    echo 'hosts: files mdns4_minimal [NOTFOUND=return] dns mdns4' >> /etc/nsswitch.conf

# EOF
