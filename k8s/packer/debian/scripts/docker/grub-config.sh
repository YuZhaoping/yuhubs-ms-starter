#!/bin/sh -eux

sed -i 's/GRUB_CMDLINE_LINUX_DEFAULT="\(.*\)"/GRUB_CMDLINE_LINUX_DEFAULT="vsyscall=emulate cgroup_enable=memory swapaccount=1 \1"/g' \
  /etc/default/grub;
update-grub;
