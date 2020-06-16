#!/bin/sh -eux

# Set a default HOME_DIR environment variable if not set
HOME_DIR="${HOME_DIR:-/home/vagrant}";


mkdir -p "$HOME_DIR/docker"

wget -c -P "$HOME_DIR/docker" https://raw.githubusercontent.com/docker/docker/master/contrib/check-config.sh

# Patch vsyscall checking
sed -i 's/is_set LEGACY_VSYSCALL_EMULATE/cat \/proc\/self\/maps | grep -q vsyscall || is_set LEGACY_VSYSCALL_EMULATE/' \
  "$HOME_DIR/docker/check-config.sh"

chmod a+x "$HOME_DIR/docker/check-config.sh"


# To test installation be working correctly
cat <<EOF >"$HOME_DIR/docker/hello-world.sh";
#!/bin/sh -eux

docker container run --rm hello-world

EOF

chmod a+x "$HOME_DIR/docker/hello-world.sh"


chown -R vagrant:vagrant "$HOME_DIR/docker"
