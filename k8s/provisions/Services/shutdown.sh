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

RUN_USER="${K8S_RUN_USER:-vagrant}"


###

phase=""
if [ "$#" -gt 0 ]; then
  phase=$1
else
  exit 3
fi

phase_shutdown_file="./_shutdown/_${phase}.sh"

if [ ! -f "${phase_shutdown_file}" ]; then
  exit 3
fi


###

node_index=0
if [ "$#" -gt 1 ]; then
  node_index=$2
fi

node_name=${K8S_SERVICE_NODE_NAMES[$node_index]}
node_ip=${K8S_SERVICE_NODE_IPS[$node_index]}

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


### Do shutdown phase
source "$phase_shutdown_file"
###
