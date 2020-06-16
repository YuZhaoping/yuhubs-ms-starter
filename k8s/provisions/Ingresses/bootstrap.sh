#!/usr/bin/env bash
#set -eux

cd $(dirname $0)


DEPLOY_HOME="."
if [ -d "/vagrant" ]; then
  DEPLOY_HOME="/vagrant"
  cd $DEPLOY_HOME
fi


###

if [ ! -f "$DEPLOY_HOME/DEPLOY.variables" ]; then
  exit 1
fi

source "$DEPLOY_HOME/DEPLOY.variables"


###

LOGIN_USER="${K8S_LOGIN_USER:-vagrant}"
USER_HOME="${K8S_USER_HOME:-/home/vagrant}"

RUN_USER="${K8S_RUN_USER:-root}"


###

node_index=0
if [ "$#" -gt 0 ]; then
  node_index=$1
fi

node_name=${K8S_INGRESS_NODE_NAMES[$node_index]}
node_ip=${K8S_INGRESS_NODE_IPS[$node_index]}

if [ "x$node_name" = "x" ]; then
  exit 2
fi


###

_DRY_RUN=""
_ROOT=""
if [ ! $DEPLOY_HOME = "/vagrant" ]; then
  _DRY_RUN="t"

  _ROOT="/tmp/k8s/$node_name"
  mkdir -p "$_ROOT"

  USER_HOME="${_ROOT}${USER_HOME}"
  mkdir -p "$USER_HOME"
fi


### hosts
if [ "n$_DRY_RUN" = "n" ]; then
  cat "$DEPLOY_HOME/hosts" >>"${_ROOT}/etc/hosts"
else
  mkdir -p "${_ROOT}/etc"
  cat "$DEPLOY_HOME/hosts"  >"${_ROOT}/etc/hosts"
fi
###
