### Shutdown: kube-apiserver, keepalived, haproxy

kube_node_markup_dir="$DEPLOY_HOME/${node_name}/kube/apiserver"

if [ -d "$kube_node_markup_dir" ]; then

  echo "--- shutdown: [${node_name}] kube-apiserver ---"
  if [ "n$_DRY_RUN" = "n" ]; then
    systemctl stop kube-apiserver
    systemctl disable kube-apiserver
  fi

  echo "--- shutdown: [${node_name}] keepalived ---"
  if [ "n$_DRY_RUN" = "n" ]; then
    systemctl stop keepalived
    systemctl disable keepalived
  fi

  echo "--- shutdown: [${node_name}] haproxy ---"
  if [ "n$_DRY_RUN" = "n" ]; then
    systemctl stop haproxy
    systemctl disable haproxy
  fi

fi
