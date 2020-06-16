#!/usr/bin/env bash

cd $(dirname $0)

export K8S_HOME=$(pwd)


./packer/build-debian-virtualbox.sh
