
SRC_DIR="$SRC_HOME/kube/kubelet"


KUBE_PKI_HOME="${K8S_KUBE_PKI_HOME:-/etc/kubernetes/pki}"
KUBE_PKI_HOME=$(echo "$KUBE_PKI_HOME" | sed 's;/;\\/;g')


kube_cluster_dns_server_ips=""

count=0
for ds in ${K8S_CLUSTER_DNS_SERVER_IPS[@]}; do
  if [ $count -gt 0 ]; then
    kube_cluster_dns_server_ips="${kube_cluster_dns_server_ips}, "
  fi

  kube_cluster_dns_server_ips="${kube_cluster_dns_server_ips}\"${ds}\""

  count=$(($count+1))
done


node_index=0
for node_name in ${K8S_KUBELET_NODE_NAMES[@]}; do
  echo "--- deploy: [${node_name}] kubelet ---"

  node_ip=${K8S_KUBELET_NODE_IPS[$node_index]}

  # Also mark up the node as kubelet node
  DEST_DIR="$DEST_HOME/$node_name/kube/kubelet"
  mkdir -p "$DEST_DIR"

  sed \
    -e "s/<kube_pki_home>/$KUBE_PKI_HOME/" \
    -e "s/<kube_node_bind_ip>/$node_ip/g" \
    -e "s/\"<kube_cluster_dns_server_ips>\"/$kube_cluster_dns_server_ips/" \
    -e "s/<kube_cluster_domain>/$K8S_SERVICE_DNS_DOMAIN/" \
    "$SRC_DIR/kubelet-config.json" \
    >"$DEST_DIR/kubelet-config.json"


  node_index=$(($node_index+1))
done


KUBE_ETC_HOME="${K8S_KUBE_ETC_HOME:-/etc/kubernetes}"
KUBE_ETC_HOME=$(echo "$KUBE_ETC_HOME" | sed 's;/;\\/;g')

KUBE_BIN_HOME=$(echo "$K8S_KUBE_BIN_HOME" | sed 's;/;\\/;g')


DEST_DIR="$DEST_HOME/kube/kubelet"
mkdir -p "$DEST_DIR"

cp -f \
  "$SRC_DIR/kubelet.env" \
  "$DEST_DIR/kubelet.env"

sed \
  -e "s/<kube_etc_home>/$KUBE_ETC_HOME/g" \
  -e "s/<k8s_run_user>/$K8S_RUN_USER/" \
  -e "s/<kube_bin_home>/$KUBE_BIN_HOME/" \
  "$SRC_DIR/kubelet.service" \
  >"$DEST_DIR/kubelet.service"
