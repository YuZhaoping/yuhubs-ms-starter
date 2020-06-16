#!/usr/bin/env bash
set -eux

apt-get update
apt-get install -y etcd

# The etcd service will start automatically after the installation,
# stop and disable it for this instance:
systemctl stop etcd
systemctl disable etcd


# Move the etcd binaries to etcd home
ETCD_HOME="/opt/k8s/etcd"

mkdir -p "$ETCD_HOME/bin"

etcd_bins=("etcd" "etcdctl")

for etcd_bin in ${etcd_bins[@]}; do
  if [ ! -L "/usr/bin/$etcd_bin" ]; then
    mv -vf "/usr/bin/$etcd_bin" "$ETCD_HOME/bin/";
    ln -s "$ETCD_HOME/bin/$etcd_bin" "/usr/bin/$etcd_bin";
  fi
done
