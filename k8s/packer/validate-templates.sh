#!/usr/bin/env bash

cd $(dirname $0)

export K8S_HOME=$(readlink -m "../")


DEFAULT_PACKER_BUILD_DIR="$K8S_HOME/build/packer"

if [ "x${PACKER_BUILD_DIR}" = "x" ]; then
  PACKER_BUILD_DIR=${DEFAULT_PACKER_BUILD_DIR};
fi


cd "./debian"

TEMPLATE_FILES=("buster-amd64-base.json" "buster-amd64-docker.json" "buster-amd64-k8s.json")

for template_file in ${TEMPLATE_FILES[@]}; do
  echo "--- Validate the \"${template_file}\" ---"
  packer validate "${template_file}"
done
