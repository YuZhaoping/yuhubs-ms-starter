#!/usr/bin/env bash
#set -eux

cd $(dirname $0)

source ./flannelctl-env.sh


$ETCDCTL_BIN_HOME/etcdctl \
  --endpoints=${KUBE_ETCD_SERVERS} \
  ls ${FLANNEL_ETCD_PREFIX}/subnets


declare -a subnets

readarray -t subnets < <($ETCDCTL_BIN_HOME/etcdctl \
  --endpoints=${KUBE_ETCD_SERVERS} \
  ls ${FLANNEL_ETCD_PREFIX}/subnets)

for subnet in ${subnets[@]}; do
  $ETCDCTL_BIN_HOME/etcdctl \
    --endpoints=${KUBE_ETCD_SERVERS} \
    get ${subnet}
done
