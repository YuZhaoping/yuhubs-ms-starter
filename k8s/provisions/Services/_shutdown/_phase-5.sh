### Shutdown: kube-proxy, kubelet

# kube-proxy
kube_node_markup_dir="$DEPLOY_HOME/${node_name}/kube/proxy"

if [ -d "$kube_node_markup_dir" ]; then

  echo "--- shutdown: [${node_name}] kube-proxy ---"
  if [ "n$_DRY_RUN" = "n" ]; then
    systemctl stop kube-proxy
    systemctl disable kube-proxy
  fi

fi

# kubelet
kube_node_markup_dir="$DEPLOY_HOME/${node_name}/kube/kubelet"

if [ -d "$kube_node_markup_dir" ]; then

  echo "--- shutdown: [${node_name}] kubelet ---"
  if [ "n$_DRY_RUN" = "n" ]; then
    systemctl stop kubelet
    systemctl disable kubelet
  fi

fi
