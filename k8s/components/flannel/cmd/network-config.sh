#!/usr/bin/env bash
#set -eux

cd $(dirname $0)

source ./flannelctl-env.sh


config_opt="get"

if [ "$#" -gt 0 ]; then
  config_opt=$1
fi


if [ "$config_opt" = "set" ]; then

$ETCDCTL_BIN_HOME/etcdctl \
  --endpoints=${KUBE_ETCD_SERVERS} \
  mkdir ${FLANNEL_ETCD_PREFIX}

$ETCDCTL_BIN_HOME/etcdctl \
  --endpoints=${KUBE_ETCD_SERVERS} \
  mk ${FLANNEL_ETCD_PREFIX}/config \
  '{"Network":"'${KUBE_CLUSTER_CIDR}'", "SubnetLen": 24, "Backend": {"Type": "vxlan"}}'

fi


$ETCDCTL_BIN_HOME/etcdctl \
  --endpoints=${KUBE_ETCD_SERVERS} \
  get ${FLANNEL_ETCD_PREFIX}/config
