#!/usr/bin/env bash

cd $(dirname $0)


build_target="apps"
if [ "$#" -gt 0 ]; then
  build_target=$1
fi

case "${build_target}" in
  "libs" | "svcs" | "apps")
    ;;
  *)
    echo "Usage: build-all.sh [libs | svcs | apps]";
    exit 1;
    ;;
esac


mvn -N install

./commons/build-all.sh
./utils/build-all.sh
./auth/build-all.sh

./services/build-pom.sh


if [ "x$build_target" = "xlibs" ]; then
  exit 0
fi

./services/auth-server/cmd/build.sh
./services/catalog/cmd/build.sh


if [ "x$build_target" = "xsvcs" ]; then
  exit 0
fi

./front-end/cmd/build.sh
