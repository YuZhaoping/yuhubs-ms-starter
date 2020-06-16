#!/bin/sh -eux

# Set a default HOME_DIR environment variable if not set
HOME_DIR="${HOME_DIR:-/home/vagrant}";

case "$PACKER_BUILDER_TYPE" in
  virtualbox-iso|virtualbox-ovf)
    VER="`cat $HOME_DIR/.vbox_version`";
    ISO="VBoxGuestAdditions_$VER.iso";
    apt-get install -y linux-headers-$(uname -r);
    apt-get install -y make;
    mkdir -p /tmp/vbox;
    mount -o loop $HOME_DIR/$ISO /tmp/vbox;
    sh /tmp/vbox/VBoxLinuxAdditions.run --nox11 \
      || echo "VBoxLinuxAdditions.run exited $? and is suppressed." \
        "For more read https://www.virtualbox.org/ticket/12479";
    umount /tmp/vbox;
    rm -rf /tmp/vbox;
    rm -f $HOME_DIR/.vbox_version;
    rm -f $HOME_DIR/*.iso;
    ;;
esac
