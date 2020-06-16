### Startup: etcd

etcd_node_markup_dir="$DEPLOY_HOME/${node_name}/etcd"

if [ -d "$etcd_node_markup_dir" ]; then

  echo "--- startup: [${node_name}] etcd ---"
  if [ "n$_DRY_RUN" = "n" ]; then
    systemctl enable etcd
    systemctl start etcd
  fi

fi
