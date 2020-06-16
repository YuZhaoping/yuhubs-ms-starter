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


### common functions

cp_systemd_service() {
  local src_dir=$1
  local svc_name=$2

  local dest_dir="/etc/systemd/system"
  if [ ! -f "$dest_dir/$svc_name" ]; then
    dest_dir="/lib/systemd/system"
  fi

  dest_dir="${_ROOT}${dest_dir}"

  if [ ! -d "$dest_dir" ]; then
    mkdir -p "$dest_dir"
  fi

  if [ -f "$dest_dir/$svc_name" ] && [ ! -f "$dest_dir/$svc_name.old" ]; then
    cp -vf "$dest_dir/$svc_name" "$dest_dir/$svc_name.old"
  fi

  cp -vf "$src_dir/$svc_name" "$dest_dir/"

  #echo "$dest_dir/$svc_name"
}


mk_work_dir() {
  local work_dir=$1

  echo "'""mkdir -p $work_dir""'"

  work_dir="${_ROOT}${work_dir}"

  mkdir -p "$work_dir"
  chmod +w "$work_dir"

  if [ "n$_DRY_RUN" = "n" ]; then
    chown -R $RUN_USER:$RUN_USER "$work_dir"
  fi
}


mk_service_workdir() {
  local service_file=$1

  local work_dir="$(cat $service_file | grep WorkingDirectory | awk -F '=' '{print $2}')"

  if [ ! "x$work_dir" = "x" ]; then
    mk_work_dir "$work_dir"
  fi

  #echo "$work_dir"
}


### hosts
if [ "n$_DRY_RUN" = "n" ]; then
  cat "$DEPLOY_HOME/hosts" >>"${_ROOT}/etc/hosts"
else
  mkdir -p "${_ROOT}/etc"
  cat "$DEPLOY_HOME/hosts"  >"${_ROOT}/etc/hosts"
fi
###

### PKI
source ./_init/_pki.sh
###

### kubeconfig
source ./_init/_kubeconfig.sh
###

### etcd
source ./_init/_etcd.sh
###

### kube-env-config
source ./_init/_kube-env-config.sh
###

### haproxy
source ./_init/LB/_haproxy.sh
###

### keepalived
source ./_init/LB/_keepalived.sh
###

### kube-apiserver
source ./_init/control-plane/_apiserver.sh
###

### kube-controller-manager
source ./_init/control-plane/_controller-manager.sh
###

### kube-scheduler
source ./_init/control-plane/_scheduler.sh
###

### flannel
source ./_init/_flannel.sh
###

### kubelet
source ./_init/_kubelet.sh
###

### kube-proxy
source ./_init/addon/_kube-proxy.sh
###

### kubectl
source ./_init/_kubectl.sh
###

if [ "n$_DRY_RUN" = "n" ]; then
  systemctl daemon-reload
fi
