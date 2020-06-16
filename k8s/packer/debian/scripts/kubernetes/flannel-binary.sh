#!/usr/bin/env bash
set -eux

FLANNEL_VER="v0.12.0"
FLANNEL_PKG="flannel-${FLANNEL_VER}-linux-amd64"

cd /tmp

rm -f "${FLANNEL_PKG}.tar.gz"
rm -rf flannel/

wget "https://github.com/coreos/flannel/releases/download/${FLANNEL_VER}/${FLANNEL_PKG}.tar.gz"

mkdir flannel
tar -xvf "${FLANNEL_PKG}.tar.gz" -C flannel


FLANNEL_HOME="/opt/k8s/flannel"

mkdir -p "$FLANNEL_HOME/bin"

cp -vf flannel/{flanneld,mk-docker-opts.sh} "$FLANNEL_HOME/bin"
chmod +x $FLANNEL_HOME/bin/{flanneld,mk-docker-opts.sh}


rm -f "${FLANNEL_PKG}.tar.gz"
rm -rf flannel/
