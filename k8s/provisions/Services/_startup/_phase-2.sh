### Startup: haproxy, keepalived, kube-apiserver

kube_node_markup_dir="$DEPLOY_HOME/${node_name}/kube/apiserver"

if [ -d "$kube_node_markup_dir" ]; then

  echo "--- startup: [${node_name}] haproxy ---"
  if [ "n$_DRY_RUN" = "n" ]; then
    systemctl enable haproxy
    systemctl start haproxy
  fi

  echo "--- startup: [${node_name}] keepalived ---"
  if [ "n$_DRY_RUN" = "n" ]; then
    systemctl enable keepalived
    systemctl start keepalived
  fi

  echo "--- startup: [${node_name}] kube-apiserver ---"
  if [ "n$_DRY_RUN" = "n" ]; then
    systemctl enable kube-apiserver
    systemctl start kube-apiserver
  fi

fi
