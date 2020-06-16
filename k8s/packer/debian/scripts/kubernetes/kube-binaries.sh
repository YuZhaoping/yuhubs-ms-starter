#!/usr/bin/env bash
set -eux

K8S_VER="v1.18.3"
K8S_SERVER_PKG="kubernetes-server-linux-amd64"

DOWNLOAD_URL="https://github.com/kubernetes/kubernetes/releases/download/${K8S_VER}/kubernetes.tar.gz"


cd /tmp

rm -f "kubernetes.tar.gz"
rm -rf k8s/

wget "${DOWNLOAD_URL}"

mkdir k8s
tar -xvf "kubernetes.tar.gz" -C k8s --strip-components=1

KUBERNETES_SKIP_CONFIRM=y ./k8s/cluster/get-kube-binaries.sh

tar xvfz "./k8s/server/${K8S_SERVER_PKG}.tar.gz" -C k8s --strip-components=1


KUBE_HOME="/opt/k8s/kube"

src_dir="k8s/server/bin"
dest_dir="$KUBE_HOME/bin"

mkdir -p "$dest_dir"

kube_binaries=("kubeadm" "kubectl")
for kube_bin in ${kube_binaries[@]}; do
  cp -vf "$src_dir/$kube_bin" "$dest_dir/"
  chmod +x "$dest_dir/$kube_bin"
done

kube_binaries=("kube-apiserver" "kube-controller-manager" "kube-scheduler" "kube-proxy")
for kube_bin in ${kube_binaries[@]}; do
  cp -vf "$src_dir/$kube_bin" "$dest_dir/"
  chmod +x "$dest_dir/$kube_bin"
done

kube_binaries=("kubelet")
for kube_bin in ${kube_binaries[@]}; do
  cp -vf "$src_dir/$kube_bin" "$dest_dir/"
  chmod +x "$dest_dir/$kube_bin"
done


rm -f "kubernetes.tar.gz"
rm -rf k8s/
