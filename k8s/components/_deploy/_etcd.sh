
SRC_DIR="$SRC_HOME/etcd"


ETCD_ETC_HOME="${K8S_ETCD_ETC_HOME:-/etc/etcd}"
ETCD_ETC_HOME=$(echo "$ETCD_ETC_HOME" | sed 's;/;\\/;g')

KUBE_PKI_HOME="${K8S_KUBE_PKI_HOME:-/etc/kubernetes/pki}"
KUBE_PKI_HOME=$(echo "$KUBE_PKI_HOME" | sed 's;/;\\/;g')

ETCD_BIN_HOME=$(echo "$K8S_ETCD_BIN_HOME" | sed 's;/;\\/;g')


etcd_cluster_entries=""

node_index=0
for node_name in ${K8S_ETCD_NODE_NAMES[@]}; do
  echo "--- deploy: [${node_name}] etcd ---"

  node_ip=${K8S_ETCD_NODE_IPS[$node_index]}

  lowercased_node_name=$(echo "$node_name" | tr '[:upper:]' '[:lower:]')


  if [ $node_index -gt 0 ]; then
    etcd_cluster_entries="${etcd_cluster_entries},"
  fi

  etcd_cluster_entries="${etcd_cluster_entries}${lowercased_node_name}=https:\/\/${node_ip}:2380"

  # Also mark up the node as etcd node
  DEST_DIR="$DEST_HOME/$node_name/etcd"
  mkdir -p "$DEST_DIR"

  sed \
    -e "s/<etcd_node_name>/$lowercased_node_name/" \
    -e "s/<etcd_node_ip>/$node_ip/g" \
    "$SRC_DIR/etcd-node.env" >"$DEST_DIR/etcd-node.env"


  node_index=$(($node_index+1))
done


DEST_DIR="$DEST_HOME/etcd"
mkdir -p "$DEST_DIR"

sed \
  -e "s/<kube_pki_home>/$KUBE_PKI_HOME/" \
  -e "s/<etcd_cluster_entries>/$etcd_cluster_entries/" \
  "$SRC_DIR/etcd-global.env" >"$DEST_DIR/etcd-global.env"

sed \
  -e "s/<etcd_etc_home>/$ETCD_ETC_HOME/g" \
  -e "s/<k8s_run_user>/$K8S_RUN_USER/" \
  -e "s/<etcd_bin_home>/$ETCD_BIN_HOME/" \
  "$SRC_DIR/etcd.service" >"$DEST_DIR/etcd.service"


### CMD

SRC_DIR="$SRC_HOME/etcd/cmd"


DEST_DIR="$DEST_HOME/etcd/cmd"
mkdir -p "$DEST_DIR"

sed \
  -e "s/<kube_pki_home>/$KUBE_PKI_HOME/" \
  -e "s/<etcd_bin_home>/$ETCD_BIN_HOME/" \
  "$SRC_DIR/etcdctl.sh" >"$DEST_DIR/etcdctl.sh"


etcd_cmds=("check-health.sh" "member-list.sh")

for cmd in ${etcd_cmds[@]}; do
  cp -f \
    "$SRC_DIR/${cmd}" \
    "$DEST_DIR/${cmd}"
done
