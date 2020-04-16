#!/usr/bin/env bash

cd $(dirname $0)


mvn -N install

cd commons/ && mvn install && mvn clean && cd ../
cd utils/ && mvn install && mvn clean && cd ../
cd auth/ && mvn install && mvn clean && cd ../

./services/auth-server/bin/build.sh
./services/catalog/bin/build.sh

./front-end/bin/build.sh
