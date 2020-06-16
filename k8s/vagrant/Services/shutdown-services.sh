#!/usr/bin/env bash

cd $(dirname $0)


VAGRANT_DIR="./Services"

if [ ! -f "$VAGRANT_DIR/Vagrantfile" ]; then
  exit 1
fi


cd $VAGRANT_DIR

for ((i=5;i>=1;i--)); do
  provision="shutdown-phase-$i"
  vagrant provision --provision-with "$provision"
done
