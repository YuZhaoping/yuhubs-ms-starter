#!/bin/sh -eux

# Set a default HOME_DIR environment variable if not set
HOME_DIR="${HOME_DIR:-/home/vagrant}";


pubkey_url="https://raw.githubusercontent.com/mitchellh/vagrant/master/keys/vagrant.pub";
mkdir -p $HOME_DIR/.ssh;
if command -v wget >/dev/null 2>&1; then
  wget --no-check-certificate "$pubkey_url" -O $HOME_DIR/.ssh/authorized_keys;
elif command -v curl >/dev/null 2>&1; then
  curl --insecure --location "$pubkey_url" > $HOME_DIR/.ssh/authorized_keys;
elif command -v fetch >/dev/null 2>&1; then
  fetch -am -o $HOME_DIR/.ssh/authorized_keys "$pubkey_url";
else
  echo "Cannot download vagrant public key";
  exit 1;
fi

chown -R vagrant:vagrant $HOME_DIR/.ssh;


if [ -f "$HOME_DIR/.bashrc" ]; then
  sed -i \
    -e 's/^HISTFILESIZE=[0-9]*$/HISTFILESIZE=0/' \
    -e 's/^HISTSIZE=[0-9]*$/HISTSIZE=32/' \
    $HOME_DIR/.bashrc;
fi


cat <<EOF >$HOME_DIR/clean-var-logs.sh;
#!/bin/sh -eux

find /var/log -type f -exec truncate --size=0 {} \;

EOF

chown -R vagrant:vagrant $HOME_DIR/clean-var-logs.sh;
chmod a+x $HOME_DIR/clean-var-logs.sh;
