#!/bin/sh -eux

# Set a default HOME_DIR environment variable if not set
HOME_DIR="${HOME_DIR:-/home/vagrant}";

# Delete all Linux headers
dpkg --list | awk '{ print $2 }' \
  | grep 'linux-headers' \
  | xargs apt-get -y purge;

# Remove specific Linux kernels, such as linux-image-3.11.0-15 but
# keeps the current kernel and does not touch the virtual packages,
# e.g. 'linux-image-amd64', etc.
dpkg --list | awk '{ print $2 }' \
  | grep 'linux-image-[234].*' \
  | grep -v `uname -r` \
  | xargs apt-get -y purge;

# Delete Linux source
dpkg --list | awk '{ print $2 }' \
  | grep linux-source \
  | xargs apt-get -y purge;

# Delete development packages
dpkg --list | awk '{ print $2 }' \
  | grep -- '-dev$' \
  | xargs apt-get -y purge;

# Delete X11 libraries
apt-get -y purge libx11-data xauth libxmuu1 libxcb1 libx11-6 libxext6;

# Delete obsolete networking
apt-get -y purge ppp pppconfig pppoeconf;

# Delete oddities
apt-get -y purge popularity-contest;
apt-get -y purge installation-report;

apt-get -y autoremove --purge;
apt-get -y clean;
apt-get -y autoclean;


# Clean up leftover dhcp leases
if [ -d "/var/lib/dhcp" ]; then
  rm -f /var/lib/dhcp/*
fi

# Remove temporary files
rm -rf /tmp/*

# Truncate any logs that have built up during the install
find /var/log -type f -exec truncate --size=0 {} \;

# Blank netplan machine-id (DUID) so machines get unique ID generated on boot.
truncate -s 0 /etc/machine-id

# Clear the history so our install isn't there
export HISTSIZE=0

rm -f /root/.wget-hsts

rm -f $HOME_DIR/.wget-hsts
rm -f $HOME_DIR/.vbox_version;
rm -f $HOME_DIR/*.iso;
