#!/usr/bin/env bash
set -eux

# Documentation:
# https://etcd.io/docs/v3.4.0/integrations/
# https://github.com/etcd-io/etcd/tree/master/etcdctl

export ETCD_PKI_HOME="<kube_pki_home>/etcd"

export ETCDCTL_CACERT=$ETCD_PKI_HOME/ca.crt
export ETCDCTL_CERT=$ETCD_PKI_HOME/healthcheck-client.crt
export ETCDCTL_KEY=$ETCD_PKI_HOME/healthcheck-client.key

<etcd_bin_home>/etcdctl $@
