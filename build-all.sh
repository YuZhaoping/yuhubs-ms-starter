#!/usr/bin/env bash

cd $(dirname $0)


mvn -N install

./commons/build-all.sh
./utils/build-all.sh
./auth/build-all.sh

./services/build-pom.sh
./services/auth-server/cmd/build.sh
./services/catalog/cmd/build.sh

./front-end/cmd/build.sh
