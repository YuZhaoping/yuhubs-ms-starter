#!/bin/sh -eux

apt-get update
apt-get install -y keepalived

# The keepalived service will start automatically after the installation,
# stop and disable it for this instance:
systemctl stop keepalived
systemctl disable keepalived
