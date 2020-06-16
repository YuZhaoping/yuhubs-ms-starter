
SRC_DIR="$SRC_HOME/flannel"


# Deploy flannel to all of the kube (including kubelet and control-plane) notes
for node_name in ${K8S_KUBE_NODE_NAMES[@]}; do
  echo "--- deploy: [${node_name}] flannel ---"

  # Also mark up the node as flannel node
  DEST_DIR="$DEST_HOME/$node_name/flannel"
  mkdir -p "$DEST_DIR"
done


KUBE_ETC_HOME="${K8S_KUBE_ETC_HOME:-/etc/kubernetes}"
KUBE_ETC_HOME=$(echo "$KUBE_ETC_HOME" | sed 's;/;\\/;g')

FLANNEL_BIN_HOME=$(echo "$K8S_FLANNEL_BIN_HOME" | sed 's;/;\\/;g')


flannel_etcd_prefix=$(echo "$K8S_FLANNEL_ETCD_PREFIX" | sed 's;/;\\/;g')
flannel_inter_host_iface="${K8S_FLANNEL_INTER_HOST_IFACE:-eth1}"


DEST_DIR="$DEST_HOME/flannel"
mkdir -p "$DEST_DIR"

sed \
  -e "s/<flannel_etcd_prefix>/$flannel_etcd_prefix/" \
  -e "s/<flannel_inter_host_iface>/$flannel_inter_host_iface/" \
  "$SRC_DIR/flanneld.env" \
  >"$DEST_DIR/flanneld.env"

sed \
  -e "s/<kube_etc_home>/$KUBE_ETC_HOME/" \
  -e "s/<flannel_bin_home>/$FLANNEL_BIN_HOME/g" \
  "$SRC_DIR/flanneld.service" \
  >"$DEST_DIR/flanneld.service"

cp -f \
  "$SRC_DIR/docker.service" \
  "$DEST_DIR/docker.service"


### CMD

SRC_DIR="$SRC_HOME/flannel/cmd"


KUBE_PKI_HOME="${K8S_KUBE_PKI_HOME:-/etc/kubernetes/pki}"
KUBE_PKI_HOME=$(echo "$KUBE_PKI_HOME" | sed 's;/;\\/;g')

KUBE_POD_NETWORK_CIDR=$(echo "$K8S_POD_NETWORK_CIDR" | sed 's;/;\\/;g')

ETCD_BIN_HOME=$(echo "$K8S_ETCD_BIN_HOME" | sed 's;/;\\/;g')


kube_etcd_servers=""

node_index=0
for node_ip in ${K8S_ETCD_NODE_IPS[@]}; do
  if [ $node_index -gt 0 ]; then
    kube_etcd_servers="${kube_etcd_servers},"
  fi

  kube_etcd_servers="${kube_etcd_servers}https:\/\/${node_ip}:2379"

  node_index=$(($node_index+1))
done


DEST_DIR="$DEST_HOME/flannel/cmd"
mkdir -p "$DEST_DIR"


sed \
  -e "s/<kube_pki_home>/$KUBE_PKI_HOME/" \
  -e "s/<kube_etcd_servers>/$kube_etcd_servers/" \
  -e "s/<flannel_etcd_prefix>/$flannel_etcd_prefix/" \
  -e "s/<kube_pod_network_cidr>/$KUBE_POD_NETWORK_CIDR/" \
  -e "s/<etcd_bin_home>/$ETCD_BIN_HOME/" \
  "$SRC_DIR/flannelctl-env.sh" \
  >"$DEST_DIR/flannelctl-env.sh"


flannel_cmds=("network-config.sh" "get-subnets.sh")

for cmd in ${flannel_cmds[@]}; do
  cp -f \
    "$SRC_DIR/${cmd}" \
    "$DEST_DIR/${cmd}"
done
