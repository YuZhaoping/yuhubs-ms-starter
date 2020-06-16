
SRC_DIR="$SRC_HOME/kube/scheduler"


# Deploy the kube-scheduler to the same with the kube-apiserver nodes
for node_name in ${K8S_APISERVER_NODE_NAMES[@]}; do
  echo "--- deploy: [${node_name}] kube-scheduler ---"

  # Also mark up the node as kube-scheduler node
  DEST_DIR="$DEST_HOME/$node_name/kube/scheduler"
  mkdir -p "$DEST_DIR"
done


KUBE_ETC_HOME="${K8S_KUBE_ETC_HOME:-/etc/kubernetes}"
KUBE_ETC_HOME=$(echo "$KUBE_ETC_HOME" | sed 's;/;\\/;g')

KUBE_BIN_HOME=$(echo "$K8S_KUBE_BIN_HOME" | sed 's;/;\\/;g')


DEST_DIR="$DEST_HOME/kube/scheduler"
mkdir -p "$DEST_DIR"

cp -f \
  "$SRC_DIR/scheduler.env" \
  "$DEST_DIR/scheduler.env"

sed \
  -e "s/<kube_etc_home>/$KUBE_ETC_HOME/g" \
  -e "s/<k8s_run_user>/$K8S_RUN_USER/" \
  -e "s/<kube_bin_home>/$KUBE_BIN_HOME/" \
  "$SRC_DIR/kube-scheduler.service" \
  >"$DEST_DIR/kube-scheduler.service"
