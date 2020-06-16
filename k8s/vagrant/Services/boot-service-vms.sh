#!/usr/bin/env bash

cd $(dirname $0)


VAGRANT_DIR="./Services"

if [ ! -f "$VAGRANT_DIR/Vagrantfile" ]; then
  exit 1
fi


cd $VAGRANT_DIR

vagrant up
