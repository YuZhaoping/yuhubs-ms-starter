
flannel_node_src_dir="$DEPLOY_HOME/${node_name}/flannel"

if [ -d "$flannel_node_src_dir" ]; then
echo "--- init: [${node_name}] flannel ---"


flannel_common_src_dir="$DEPLOY_HOME/flannel"

cp_systemd_service "$flannel_common_src_dir" "flanneld.service"
cp_systemd_service "$flannel_common_src_dir" "docker.service"


flanneld_env_dest_dir="/etc/flanneld"
flanneld_env_dest_dir=$(echo "${_ROOT}$flanneld_env_dest_dir" | sed 's/\\//g')

mkdir -p "$flanneld_env_dest_dir"


cp -vf $flannel_common_src_dir/*.env "$flanneld_env_dest_dir/"
#cp -vf $flannel_node_src_dir/*.env "$flanneld_env_dest_dir/"

fi


### CMD

cmd_dest_dir=$(echo "${_ROOT}${K8S_INSTALL_HOME}/flannel" | sed 's/\\//g')

mkdir -p "$cmd_dest_dir"


flannel_cmds=("flannelctl-env.sh" "network-config.sh" "get-subnets.sh")

for cmd in ${flannel_cmds[@]}; do
  cp -vf "$DEPLOY_HOME/flannel/cmd/$cmd" "$cmd_dest_dir/"
  chmod a+x "$cmd_dest_dir/$cmd"
done
