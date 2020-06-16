
kube_node_src_dir="$DEPLOY_HOME/${node_name}/kube/apiserver"

if [ -d "$kube_node_src_dir" ]; then
echo "--- init: [${node_name}] kube-apiserver ---"


kube_common_src_dir="$DEPLOY_HOME/kube/apiserver"

cp_systemd_service "$kube_common_src_dir" "kube-apiserver.service"


kube_env_dest_dir="${K8S_KUBE_ETC_HOME:-/etc/kubernetes}"
kube_env_dest_dir=$(echo "${_ROOT}$kube_env_dest_dir" | sed 's/\\//g')

mkdir -p "$kube_env_dest_dir"


cp -vf $kube_common_src_dir/*.env "$kube_env_dest_dir/"
cp -vf $kube_common_src_dir/*.csv "$kube_env_dest_dir/"

#cp -vf $kube_node_src_dir/*.env "$kube_env_dest_dir/"


if [ "n$_DRY_RUN" = "n" ]; then
  chown -R $RUN_USER:$RUN_USER "$kube_env_dest_dir"
fi

fi
