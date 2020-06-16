#!/usr/bin/env bash

cd $(dirname $0)


./pki/deploy-all.sh

./components/deploy-all.sh

./addons/deploy-all.sh

./provisions/deploy-all.sh

./vagrant/deploy-all.sh
