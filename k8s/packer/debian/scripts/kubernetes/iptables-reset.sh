#!/bin/sh -eux

# The current kubeadm packages are not compatible with the nftables backend.
# To avoid any issues, switch the iptables tooling to their legacy mode:
update-alternatives --set iptables /usr/sbin/iptables-legacy
update-alternatives --set ip6tables /usr/sbin/ip6tables-legacy
#update-alternatives --set arptables /usr/sbin/arptables-legacy
#update-alternatives --set ebtables /usr/sbin/ebtables-legacy

# Flush all chain rules (-F), and delete all non-default chains (-X),
# and flush the nat table rules (-F -t nat), then delete non-default nat table chains (-X -t nat).
iptables -F && iptables -X && iptables -F -t nat && iptables -X -t nat

# Ensure the policy on FORWARD chain to ACCEPT target.
iptables -P FORWARD ACCEPT
