#!/bin/sh -eux

# Whiteout /boot
count=$(df --sync -kP /boot | tail -n1 | awk -F ' ' '{print $4}')
count=$(($count-1))
dd if=/dev/zero of=/boot/whitespace bs=1M count=$count || echo "dd exit code $? is suppressed";
rm -f /boot/whitespace

# Whiteout root
count=$(df --sync -kP / | tail -n1 | awk -F ' ' '{print $4}')
count=$(($count-1))
dd if=/dev/zero of=/whitespace bs=1M count=$count || echo "dd exit code $? is suppressed";
rm -f /whitespace

# Whiteout /usr
count=$(df --sync -kP /usr | tail -n1 | awk -F ' ' '{print $4}')
count=$(($count-1))
dd if=/dev/zero of=/usr/whitespace bs=1M count=$count || echo "dd exit code $? is suppressed";
rm -f /usr/whitespace

# Whiteout /opt
count=$(df --sync -kP /opt | tail -n1 | awk -F ' ' '{print $4}')
count=$(($count-1))
dd if=/dev/zero of=/opt/whitespace bs=1M count=$count || echo "dd exit code $? is suppressed";
rm -f /opt/whitespace

# Whiteout /tmp
count=$(df --sync -kP /tmp | tail -n1 | awk -F ' ' '{print $4}')
count=$(($count-1))
dd if=/dev/zero of=/tmp/whitespace bs=1M count=$count || echo "dd exit code $? is suppressed";
rm -f /tmp/whitespace

# Whiteout /home
count=$(df --sync -kP /home | tail -n1 | awk -F ' ' '{print $4}')
count=$(($count-1))
dd if=/dev/zero of=/home/whitespace bs=1M count=$count || echo "dd exit code $? is suppressed";
rm -f /home/whitespace

# Whiteout /var
count=$(df --sync -kP /var | tail -n1 | awk -F ' ' '{print $4}')
count=$(($count-1))
dd if=/dev/zero of=/var/whitespace bs=1M count=$count || echo "dd exit code $? is suppressed";
rm -f /var/whitespace

sync;
