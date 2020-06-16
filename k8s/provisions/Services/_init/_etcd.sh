
etcd_node_src_dir="$DEPLOY_HOME/${node_name}/etcd"

if [ -d "$etcd_node_src_dir" ]; then
echo "--- init: [${node_name}] etcd ---"


etcd_common_src_dir="$DEPLOY_HOME/etcd"

cp_systemd_service "$etcd_common_src_dir" "etcd.service"
mk_service_workdir "$etcd_common_src_dir/etcd.service"


etcd_env_dest_dir="${K8S_ETCD_ETC_HOME:-/etc/etcd}"
etcd_env_dest_dir=$(echo "${_ROOT}$etcd_env_dest_dir" | sed 's/\\//g')

mkdir -p "$etcd_env_dest_dir"


cp -vf $etcd_common_src_dir/*.env "$etcd_env_dest_dir/"
cp -vf $etcd_node_src_dir/*.env "$etcd_env_dest_dir/"

if [ "n$_DRY_RUN" = "n" ]; then
  chown -R $RUN_USER:$RUN_USER "$etcd_env_dest_dir"
fi

fi


### CMD

cmd_dest_dir=$(echo "${_ROOT}${K8S_INSTALL_HOME}/etcd" | sed 's/\\//g')

mkdir -p "$cmd_dest_dir"


etcd_cmds=("etcdctl.sh" "check-health.sh" "member-list.sh")

for cmd in ${etcd_cmds[@]}; do
  cp -vf "$DEPLOY_HOME/etcd/cmd/$cmd" "$cmd_dest_dir/"
  chmod a+x "$cmd_dest_dir/$cmd"
done
