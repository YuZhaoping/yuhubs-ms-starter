#!/usr/bin/env bash

cd $(dirname $0)

export K8S_HOME=$(pwd)


source "$K8S_HOME/DEPLOY.variables"


DEFAULT_PACKER_BUILD_DIR="$K8S_HOME/build/packer"

if [ "x${PACKER_BUILD_DIR}" = "x" ]; then
  PACKER_BUILD_DIR=${DEFAULT_PACKER_BUILD_DIR}
fi


BOX_BASENAME="$K8S_VAGRANT_BOX_BASENAME"
VAGRANT_BOX_FILE="${PACKER_BUILD_DIR}/VirtualBox/${BOX_BASENAME}/${BOX_BASENAME}.box"
VAGRANT_BOX_NAME="yuhubs.k8s.vbox/${BOX_BASENAME}"


if [ -f "$VAGRANT_BOX_FILE" ]; then
  vagrant box add --name "${VAGRANT_BOX_NAME}" "${VAGRANT_BOX_FILE}"
fi
