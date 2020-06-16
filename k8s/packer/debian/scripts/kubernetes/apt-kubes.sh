#!/usr/bin/env bash
set -eux

# Update the apt package index and install packages to allow apt to use a repository over HTTPS:
apt-get update
apt-get install -y apt-transport-https gnupg2

# Add Kubernetes’s official GPG key:
curl -s https://packages.cloud.google.com/apt/doc/apt-key.gpg | apt-key add -

# Set up the packages' repository:
echo "deb https://apt.kubernetes.io/ kubernetes-xenial main" | tee -a /etc/apt/sources.list.d/k8s.list

# Update the apt package index, and install kubeadm, kubelet and kubectl.
apt-get update
apt-get install -y kubelet kubeadm kubectl
apt-mark hold kubelet kubeadm kubectl

# The kubelet service will start automatically after the installation,
# stop and disable it for this instance:
systemctl stop kubelet
systemctl disable kubelet


# Move the kube binaries to kube home
KUBE_HOME="/opt/k8s/kube"

mkdir -p "$KUBE_HOME/bin"

kube_bins=("kubelet" "kubeadm" "kubectl")

for kube_bin in ${kube_bins[@]}; do
  if [ ! -L "/usr/bin/$kube_bin" ]; then
    mv -vf "/usr/bin/$kube_bin" "$KUBE_HOME/bin/";
    ln -s "$KUBE_HOME/bin/$kube_bin" "/usr/bin/$kube_bin";
  fi
done
