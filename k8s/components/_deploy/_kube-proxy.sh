
SRC_DIR="$SRC_HOME/kube/proxy"


# Deploy kube-proxy to all of the kubelet nodes
for node_name in ${K8S_KUBELET_NODE_NAMES[@]}; do
  echo "--- deploy: [${node_name}] kube-proxy ---"

  # Also mark up the node as kube-proxy node
  DEST_DIR="$DEST_HOME/$node_name/kube/proxy"
  mkdir -p "$DEST_DIR"
done


KUBE_ETC_HOME="${K8S_KUBE_ETC_HOME:-/etc/kubernetes}"
KUBE_ETC_HOME=$(echo "$KUBE_ETC_HOME" | sed 's;/;\\/;g')

KUBE_BIN_HOME=$(echo "$K8S_KUBE_BIN_HOME" | sed 's;/;\\/;g')


DEST_DIR="$DEST_HOME/kube/proxy"
mkdir -p "$DEST_DIR"

cp -f \
  "$SRC_DIR/proxy.env" \
  "$DEST_DIR/proxy.env"

sed \
  -e "s/<kube_etc_home>/$KUBE_ETC_HOME/g" \
  -e "s/<k8s_run_user>/$K8S_RUN_USER/" \
  -e "s/<kube_bin_home>/$KUBE_BIN_HOME/" \
  "$SRC_DIR/kube-proxy.service" \
  >"$DEST_DIR/kube-proxy.service"
