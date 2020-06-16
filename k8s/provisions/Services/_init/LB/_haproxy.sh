
haproxy_node_markup_dir="$DEPLOY_HOME/${node_name}/kube/apiserver"

if [ -d "$haproxy_node_markup_dir" ]; then
echo "--- init: [${node_name}] haproxy ---"


haproxy_cfg_src_dir="$DEPLOY_HOME/haproxy"


haproxy_cfg_dest_dir="/etc/haproxy"
haproxy_cfg_dest_dir=$(echo "${_ROOT}$haproxy_cfg_dest_dir" | sed 's/\\//g')

mkdir -p "$haproxy_cfg_dest_dir"


cp -vf $haproxy_cfg_src_dir/*.cfg "$haproxy_cfg_dest_dir/"

fi
