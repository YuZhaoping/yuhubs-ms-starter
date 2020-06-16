#!/bin/sh -eux

set +e
swapuuid=$(/sbin/blkid -o value -l -s UUID -t TYPE=swap)
case "$?" in
  2|0) ;;
  *) exit 1 ;;
esac
set -e

if [ "x${swapuuid}" != "x" ]; then
  # Whiteout the swap partition to reduce box size
  # Swap is disabled till reboot
  swappart="`readlink -f /dev/disk/by-uuid/$swapuuid`";
  /sbin/swapoff "$swappart";
  dd if=/dev/zero of="$swappart" bs=1M || echo "dd exit code $? is suppressed";
  /sbin/mkswap -U "$swapuuid" "$swappart";
fi
