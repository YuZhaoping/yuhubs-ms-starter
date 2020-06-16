#!/bin/sh -eux

# Truncate the history size
cat <<EOF >>/etc/profile;

HISTFILESIZE=0
HISTSIZE=32

EOF

# For share folders
mkdir /mnt/host;
chmod a+w /mnt/host;
