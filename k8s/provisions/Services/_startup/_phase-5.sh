### Startup: kubelet, kube-proxy

# kubelet
kube_node_markup_dir="$DEPLOY_HOME/${node_name}/kube/kubelet"

if [ -d "$kube_node_markup_dir" ]; then

  echo "--- startup: [${node_name}] kubelet ---"
  if [ "n$_DRY_RUN" = "n" ]; then
    if [ ! -f "$DEPLOY_HOME/kube/.kubelet-bootstrap-role-bined" ]; then
      $DEPLOY_HOME/kube/admin/bind-bootstrap-role.sh
      touch "$DEPLOY_HOME/kube/..kubelet-bootstrap-role-bined"
    fi

    systemctl enable kubelet
    systemctl start kubelet
  fi

fi

# kube-proxy
kube_node_markup_dir="$DEPLOY_HOME/${node_name}/kube/proxy"

if [ -d "$kube_node_markup_dir" ]; then

  echo "--- startup: [${node_name}] kube-proxy ---"
  if [ "n$_DRY_RUN" = "n" ]; then
    systemctl enable kube-proxy
    systemctl start kube-proxy
  fi

fi
