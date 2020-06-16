#!/bin/sh -eux

# Update the apt package index and install packages to allow apt to use a repository over HTTPS:
apt-get update;

apt-get install -y \
  apt-transport-https \
  ca-certificates \
  curl \
  gnupg-agent \
  software-properties-common;

# Add Docker’s official GPG key:
curl -fsSL https://download.docker.com/linux/debian/gpg | apt-key add -

# Set up the stable repository:
echo "deb [arch=amd64] https://download.docker.com/linux/debian $(lsb_release -cs) stable" | tee /etc/apt/sources.list.d/docker.list

# Update the apt package index, and nstall the latest version of Docker Engine and containerd:
apt-get update;
apt-get install -y docker-ce docker-ce-cli containerd.io;

# Create the docker group and add your user:
groupadd -f docker;
usermod -aG docker vagrant;

# The docker service will start automatically after the installation,
# stop and disable it for this instance:
#systemctl stop docker;
#systemctl disable docker;
