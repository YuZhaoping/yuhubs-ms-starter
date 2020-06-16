
SRC_DIR="$SRC_HOME/kube/controller-manager"


# Deploy the kube-controller-manager to the same with the kube-apiserver nodes
for node_name in ${K8S_APISERVER_NODE_NAMES[@]}; do
  echo "--- deploy: [${node_name}] kube-controller-manager ---"

  # Also mark up the node as kube-controller-manager node
  DEST_DIR="$DEST_HOME/$node_name/kube/controller-manager"
  mkdir -p "$DEST_DIR"
done


KUBE_ETC_HOME="${K8S_KUBE_ETC_HOME:-/etc/kubernetes}"
KUBE_ETC_HOME=$(echo "$KUBE_ETC_HOME" | sed 's;/;\\/;g')

KUBE_BIN_HOME=$(echo "$K8S_KUBE_BIN_HOME" | sed 's;/;\\/;g')

kube_cluster_name="${K8S_CLUSTER_NAME:-default-cluster}"


DEST_DIR="$DEST_HOME/kube/controller-manager"
mkdir -p "$DEST_DIR"

sed \
  -e "s/<kube_cluster_name>/$kube_cluster_name/" \
  "$SRC_DIR/controller-manager.env" \
  >"$DEST_DIR/controller-manager.env"

sed \
  -e "s/<kube_etc_home>/$KUBE_ETC_HOME/g" \
  -e "s/<k8s_run_user>/$K8S_RUN_USER/" \
  -e "s/<kube_bin_home>/$KUBE_BIN_HOME/" \
  "$SRC_DIR/kube-controller-manager.service" \
  >"$DEST_DIR/kube-controller-manager.service"
