#!/usr/bin/env bash
set -eux

ETCD_VER="v3.4.3"
ETCD_PKG="etcd-${ETCD_VER}-linux-amd64"

# Choose either URL
GOOGLE_URL=https://storage.googleapis.com/etcd
GITHUB_URL=https://github.com/etcd-io/etcd/releases/download
DOWNLOAD_URL=${GITHUB_URL}


cd /tmp

rm -f "${ETCD_PKG}.tar.gz"
rm -rf etcd/

wget "${DOWNLOAD_URL}/${ETCD_VER}/${ETCD_PKG}.tar.gz"

mkdir etcd
tar -xvf "${ETCD_PKG}.tar.gz" -C etcd --strip-components=1

#./etcd/etcd --version
#./etcd/etcdctl version

ETCD_HOME="/opt/k8s/etcd"

mkdir -p "$ETCD_HOME/bin"

cp -vf etcd/{etcd,etcdctl} "$ETCD_HOME/bin"
chmod +x $ETCD_HOME/bin/{etcd,etcdctl}


rm -f "${ETCD_PKG}.tar.gz"
rm -rf etcd/
