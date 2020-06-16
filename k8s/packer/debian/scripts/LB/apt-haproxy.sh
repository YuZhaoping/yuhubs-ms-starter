#!/bin/sh -eux

apt-get update
apt-get install -y haproxy

# The haproxy service will start automatically after the installation,
# stop and disable it for this instance:
systemctl stop haproxy
systemctl disable haproxy
