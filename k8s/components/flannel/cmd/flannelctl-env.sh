#!/usr/bin/env bash
#set -eux


export KUBE_PKI_HOME="<kube_pki_home>"

export ETCDCTL_CACERT=$KUBE_PKI_HOME/etcd/ca.crt
export ETCDCTL_CERT=$KUBE_PKI_HOME/flannel/flanneld.crt
export ETCDCTL_KEY=$KUBE_PKI_HOME/flannel/flanneld.key

export KUBE_ETCD_SERVERS="<kube_etcd_servers>"

export FLANNEL_ETCD_PREFIX="<flannel_etcd_prefix>"
export KUBE_CLUSTER_CIDR="<kube_pod_network_cidr>"

export ETCDCTL_BIN_HOME="<etcd_bin_home>"
