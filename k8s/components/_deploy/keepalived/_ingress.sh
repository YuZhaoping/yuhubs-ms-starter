
SRC_DIR="$SRC_HOME/LB/keepalived/ingress"


NODE_DEST_HOME="$INGRESS_DEST_HOME"

global_router_id="K8S-INGRESS-KEEPALIVED"
vrrp_instance_name="VI-K8S-INGRESS"
vrrp_router_id=52

vrrp_virtual_ipaddress="$K8S_INGRESS_VIRTUAL_IP"
NODE_NAMES=${K8S_INGRESS_NODE_NAMES[@]}


source "./_deploy/keepalived/_common.sh"
