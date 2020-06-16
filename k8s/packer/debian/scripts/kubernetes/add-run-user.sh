#!/bin/sh -eux

addgroup --system k8s

adduser --system \
  --ingroup k8s \
  --home /home/kubernetes \
  --shell /bin/bash \
  --disabled-password \
  --gecos "Kubernetes" \
  k8s

usermod -aG docker k8s
