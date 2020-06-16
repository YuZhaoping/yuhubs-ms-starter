
SRC_DIR="$SRC_HOME/LB/haproxy"


DEST_DIR="$DEST_HOME/haproxy"

mkdir -p "$DEST_DIR"


virtual_server_name="kube_apiserver"
virtual_server_listen_port=${K8S_APISERVER_VIRTUAL_PORT}
actual_server_port=${K8S_APISERVER_BIND_PORT}

actual_server_nodes=""

server_options="check inter 2000 fall 2 rise 2 weight 1"
node_index=0
for node_name in ${K8S_APISERVER_NODE_NAMES[@]}; do
  echo "--- deploy: [${node_name}] haproxy ---"

  node_ip=${K8S_APISERVER_NODE_IPS[$node_index]}

  lowercased_node_name=$(echo "$node_name" | tr '[:upper:]' '[:lower:]')


  server_address="${node_ip}:${actual_server_port}"

  actual_server_nodes="${actual_server_nodes}\\tserver ${lowercased_node_name} ${server_address} ${server_options}\\n"

  node_index=$(($node_index+1))
done

sed \
  -e "s/<virtual_server_name>/$virtual_server_name/" \
  -e "s/<virtual_server_listen_port>/$virtual_server_listen_port/" \
  -e "s/<actual_server_nodes>/$actual_server_nodes/" \
  "$SRC_DIR/haproxy.cfg" \
  >"$DEST_DIR/haproxy.cfg"
