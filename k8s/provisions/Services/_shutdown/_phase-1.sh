### Shutdown: etcd

etcd_node_markup_dir="$DEPLOY_HOME/${node_name}/etcd"

if [ -d "$etcd_node_markup_dir" ]; then

  echo "--- shutdown: [${node_name}] etcd ---"
  if [ "n$_DRY_RUN" = "n" ]; then
    systemctl stop etcd
    systemctl disable etcd
  fi

fi
