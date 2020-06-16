#!/usr/bin/env bash

cd $(dirname $0)

export K8S_HOME=$(readlink -m "../")


DEPLOY_HOME="../build/deploy"

DEST_HOME="$DEPLOY_HOME/Services"
INGRESS_DEST_HOME="$DEPLOY_HOME/Ingresses"

SRC_HOME="."


mkdir -p "$DEST_HOME"
mkdir -p "$INGRESS_DEST_HOME"


source "$K8S_HOME/DEPLOY.variables"

### etcd
source ./_deploy/_etcd.sh

### kube
source ./_deploy/_kube-env-config.sh

### kube control-plane
source ./_deploy/_haproxy.sh
source ./_deploy/keepalived/_apiserver.sh

source ./_deploy/_apiserver.sh

source ./_deploy/_controller-manager.sh

source ./_deploy/_scheduler.sh

### flannel
source ./_deploy/_flannel.sh

### kube kublet
source ./_deploy/_kubelet.sh

source ./_deploy/_kube-proxy.sh


#source ./_deploy/keepalived/_ingress.sh
