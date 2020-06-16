
SRC_DIR="$SRC_HOME/kube"


KUBE_ETC_HOME="${K8S_KUBE_ETC_HOME:-/etc/kubernetes}"
KUBE_ETC_HOME=$(echo "$KUBE_ETC_HOME" | sed 's;/;\\/;g')

KUBE_PKI_HOME="${K8S_KUBE_PKI_HOME:-/etc/kubernetes/pki}"
KUBE_PKI_HOME=$(echo "$KUBE_PKI_HOME" | sed 's;/;\\/;g')

KUBE_POD_NETWORK_CIDR=$(echo "$K8S_POD_NETWORK_CIDR" | sed 's;/;\\/;g')
KUBE_SERVICE_CIDR=$(echo "$K8S_SERVICE_CIDR" | sed 's;/;\\/;g')


kube_etcd_servers=""

node_index=0
for node_ip in ${K8S_ETCD_NODE_IPS[@]}; do
  if [ $node_index -gt 0 ]; then
    kube_etcd_servers="${kube_etcd_servers},"
  fi

  kube_etcd_servers="${kube_etcd_servers}https:\/\/${node_ip}:2379"

  node_index=$(($node_index+1))
done


node_index=0
for node_name in ${K8S_KUBE_NODE_NAMES[@]}; do
  echo "--- deploy: [${node_name}] kube-env-config ---"

  node_ip=${K8S_KUBE_NODE_IPS[$node_index]}

  lowercased_node_name=$(echo "$node_name" | tr '[:upper:]' '[:lower:]')


  DEST_DIR="$DEST_HOME/$node_name/kube"
  mkdir -p "$DEST_DIR"

  sed \
    -e "s/<kube_node_name>/$lowercased_node_name/" \
    -e "s/<kube_node_ip>/$node_ip/" \
    "$SRC_DIR/kube-node.env" >"$DEST_DIR/kube-node.env"


  node_index=$(($node_index+1))
done


DEST_DIR="$DEST_HOME/kube"
mkdir -p "$DEST_DIR"

sed \
  -e "s/<kube_apiserver_advertise_ip>/$K8S_APISERVER_VIRTUAL_IP/" \
  -e "s/<kube_apiserver_advertise_port>/$K8S_APISERVER_VIRTUAL_PORT/" \
  -e "s/<kube_etcd_servers>/$kube_etcd_servers/" \
  -e "s/<kube_pod_network_cidr>/$KUBE_POD_NETWORK_CIDR/" \
  -e "s/<kube_service_cidr>/$KUBE_SERVICE_CIDR/" \
  -e "s/<kube_etc_home>/$KUBE_ETC_HOME/" \
  -e "s/<kube_pki_home>/$KUBE_PKI_HOME/" \
  "$SRC_DIR/kube-global.env" >"$DEST_DIR/kube-global.env"
