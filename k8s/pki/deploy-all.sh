#!/usr/bin/env bash

cd $(dirname $0)

export K8S_HOME=$(readlink -m "../")


export PKI_BUILD_HOME="../build/pki"
export PKI_DEPLOY_HOME="../build/deploy/Services/pki"


./_root-ca.sh

./_etcd-ca.sh
./_etcd-certs.sh

./_flannel-etcd-certs.sh

./_front-proxy-ca.sh
./_front-proxy-certs.sh

./_kube-ca.sh
./_kube-certs.sh

./_accounts-certs.sh

./_sa-keypair.sh
