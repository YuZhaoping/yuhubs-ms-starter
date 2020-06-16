
SRC_DIR="$SRC_HOME/kube/apiserver"


for node_name in ${K8S_APISERVER_NODE_NAMES[@]}; do
  echo "--- deploy: [${node_name}] kube-apiserver ---"

  # Also mark up the node as kube-apiserver node
  DEST_DIR="$DEST_HOME/$node_name/kube/apiserver"
  mkdir -p "$DEST_DIR"
done


KUBE_ETC_HOME="${K8S_KUBE_ETC_HOME:-/etc/kubernetes}"
KUBE_ETC_HOME=$(echo "$KUBE_ETC_HOME" | sed 's;/;\\/;g')

KUBE_BIN_HOME=$(echo "$K8S_KUBE_BIN_HOME" | sed 's;/;\\/;g')

kube_apiserver_count=${#K8S_APISERVER_NODE_NAMES[@]}


DEST_DIR="$DEST_HOME/kube/apiserver"
mkdir -p "$DEST_DIR"

sed \
  -e "s/<kube_apiserver_count>/$kube_apiserver_count/" \
  -e "s/<kube_apiserver_secure_port>/$K8S_APISERVER_BIND_PORT/" \
  -e "s/<kube_service_node_port_range>/$K8S_SERVICE_NODE_PORT_RANGE/" \
  "$SRC_DIR/apiserver.env" \
  >"$DEST_DIR/apiserver.env"

sed \
  -e "s/<kube_etc_home>/$KUBE_ETC_HOME/g" \
  -e "s/<k8s_run_user>/$K8S_RUN_USER/" \
  -e "s/<kube_bin_home>/$KUBE_BIN_HOME/" \
  "$SRC_DIR/kube-apiserver.service" \
  >"$DEST_DIR/kube-apiserver.service"
