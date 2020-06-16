#!/bin/sh -eux

sed -i 's/GRUB_CMDLINE_LINUX_DEFAULT="\(.*\)quiet\(.*\)"/GRUB_CMDLINE_LINUX_DEFAULT="\1\2"/' /etc/default/grub;
update-grub;
