
kube_node_src_dir="$DEPLOY_HOME/${node_name}/kube"

if [ -d "$kube_node_src_dir" ]; then
echo "--- init: [${node_name}] kube-env-config ---"


kube_common_src_dir="$DEPLOY_HOME/kube"


kube_env_dest_dir="${K8S_KUBE_ETC_HOME:-/etc/kubernetes}"
kube_env_dest_dir=$(echo "${_ROOT}$kube_env_dest_dir" | sed 's/\\//g')

mkdir -p "$kube_env_dest_dir"


global_env_file=$kube_common_src_dir/kube-global.env

cp -vf $global_env_file "$kube_env_dest_dir/"
cp -vf $kube_node_src_dir/kube-node.env "$kube_env_dest_dir/"


if [ "n$_DRY_RUN" = "n" ]; then
  chown -R $RUN_USER:$RUN_USER "$kube_env_dest_dir"
fi


kube_log_dir="$(cat $global_env_file | grep KUBE_LOG_DIR | awk -F '=' '{print $2}')"

if [ ! "x$kube_log_dir" = "x" ]; then
  mk_work_dir "$kube_log_dir"
fi

fi
