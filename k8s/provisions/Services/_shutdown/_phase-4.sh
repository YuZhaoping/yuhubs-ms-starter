### Shutdown: docker, flanneld

flannel_node_markup_dir="$DEPLOY_HOME/${node_name}/flannel"

if [ -d "$flannel_node_markup_dir" ]; then

  echo "--- shutdown: [${node_name}] docker ---"
  if [ "n$_DRY_RUN" = "n" ]; then
    systemctl stop docker
    systemctl disable docker
  fi

  echo "--- shutdown: [${node_name}] flanneld ---"
  if [ "n$_DRY_RUN" = "n" ]; then
    systemctl stop flanneld
    systemctl disable flanneld
  fi

fi
