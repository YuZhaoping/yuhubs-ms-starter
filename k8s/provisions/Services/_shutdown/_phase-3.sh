### Shutdown kube-scheduler, kube-controller-manager

# kube-scheduler
kube_node_markup_dir="$DEPLOY_HOME/${node_name}/kube/scheduler"

if [ -d "$kube_node_markup_dir" ]; then

  echo "--- shutdown: [${node_name}] kube-scheduler ---"
  if [ "n$_DRY_RUN" = "n" ]; then
    systemctl stop kube-scheduler
    systemctl disable kube-scheduler
  fi

fi

# kube-controller-manager
kube_node_markup_dir="$DEPLOY_HOME/${node_name}/kube/controller-manager"

if [ -d "$kube_node_markup_dir" ]; then

  echo "--- shutdown: [${node_name}] kube-controller-manager ---"
  if [ "n$_DRY_RUN" = "n" ]; then
    systemctl stop kube-controller-manager
    systemctl disable kube-controller-manager
  fi

fi
