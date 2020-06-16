#!/bin/sh -eux

# Configure to load IPVS kernel modules at boot time.
cat <<EOF >/etc/modules-load.d/ipvs.conf;
ip_vs
ip_vs_rr
ip_vs_wrr
ip_vs_sh
EOF


# Now, load these IPVS modules manually.
modprobe -- ip_vs
modprobe -- ip_vs_rr
modprobe -- ip_vs_wrr
modprobe -- ip_vs_sh


# Install the userspace utilities about IPVS.
apt-get update
apt-get install -y ipset ipvsadm conntrack
