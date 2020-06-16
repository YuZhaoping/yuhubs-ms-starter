#!/usr/bin/env bash

cd $(dirname $0)

export K8S_HOME=$(readlink -m "../")


DEFAULT_PACKER_BUILD_DIR="$K8S_HOME/build/packer"

if [ "x${PACKER_BUILD_DIR}" = "x" ]; then
  PACKER_BUILD_DIR=${DEFAULT_PACKER_BUILD_DIR};
fi


cd "./debian"

TEMPLATE_FILES=("buster-amd64-base.json" "buster-amd64-docker.json" "buster-amd64-k8s.json")
IMAGE_NAMES=("null" "Debian-AMD64" "Debian-Docker" "Debian-K8S")

index=0
for template_file in ${TEMPLATE_FILES[@]}; do

  BASE_IMAGE_NAME=${IMAGE_NAMES[$index]}
  index=$(($index + 1))
  IMAGE_NAME=${IMAGE_NAMES[$index]}

  BUILD_IMAGE_PATH="$PACKER_BUILD_DIR/VirtualBox/$IMAGE_NAME/$IMAGE_NAME.ovf"

  echo "--- Build \"${BUILD_IMAGE_PATH}\" ---"
  echo "--- with template \"${template_file}\" ---"

  if [ ! -f "$BUILD_IMAGE_PATH" ]; then
    echo ">>>>>>>>";
    packer build -var basename="$BASE_IMAGE_NAME" -var name="$IMAGE_NAME" "${template_file}";
    echo "<<<<<<<<";
  fi

done
