
keepalived_node_markup_dir="$DEPLOY_HOME/${node_name}/kube/apiserver"

if [ -d "$keepalived_node_markup_dir" ]; then
echo "--- init: [${node_name}] keepalived ---"


keepalived_conf_src_dir="$DEPLOY_HOME/${node_name}/keepalived"


keepalived_conf_dest_dir="/etc/keepalived"
keepalived_conf_dest_dir=$(echo "${_ROOT}$keepalived_conf_dest_dir" | sed 's/\\//g')

mkdir -p "$keepalived_conf_dest_dir"


cp -vf $keepalived_conf_src_dir/*.conf "$keepalived_conf_dest_dir/"

fi
