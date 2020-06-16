#!/usr/bin/env bash

cd $(dirname $0)

./etcdctl.sh member list
