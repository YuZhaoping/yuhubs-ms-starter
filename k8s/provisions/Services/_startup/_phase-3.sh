### Startup: kube-controller-manager, kube-scheduler

# kube-controller-manager
kube_node_markup_dir="$DEPLOY_HOME/${node_name}/kube/controller-manager"

if [ -d "$kube_node_markup_dir" ]; then

  echo "--- startup: [${node_name}] kube-controller-manager ---"
  if [ "n$_DRY_RUN" = "n" ]; then
    systemctl enable kube-controller-manager
    systemctl start kube-controller-manager
  fi

fi

# kube-scheduler
kube_node_markup_dir="$DEPLOY_HOME/${node_name}/kube/scheduler"

if [ -d "$kube_node_markup_dir" ]; then

  echo "--- startup: [${node_name}] kube-scheduler ---"
  if [ "n$_DRY_RUN" = "n" ]; then
    systemctl enable kube-scheduler
    systemctl start kube-scheduler
  fi

fi
