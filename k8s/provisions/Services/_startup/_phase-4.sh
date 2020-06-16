### Startup: flanneld, docker

flannel_node_markup_dir="$DEPLOY_HOME/${node_name}/flannel"

if [ -d "$flannel_node_markup_dir" ]; then

  if [ "n$_DRY_RUN" = "n" ]; then
    systemctl stop docker
  fi

  echo "--- startup: [${node_name}] flanneld ---"
  if [ "n$_DRY_RUN" = "n" ]; then
    if [ ! -f "$DEPLOY_HOME/flannel/.network-configured" ]; then
      $DEPLOY_HOME/flannel/cmd/network-config.sh set
      touch "$DEPLOY_HOME/flannel/.network-configured"
    fi

    systemctl enable flanneld
    systemctl start flanneld
  fi

  echo "--- restart: [${node_name}] docker ---"
  if [ "n$_DRY_RUN" = "n" ]; then
    systemctl enable docker
    systemctl start docker
  fi

fi
