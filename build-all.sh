#!/usr/bin/env bash

cd $(dirname $0)


mvn -N install

./commons/build-all.sh
./utils/build-all.sh
./auth/build-all.sh

./services/auth-server/bin/build.sh
./services/catalog/bin/build.sh

./front-end/bin/build.sh
