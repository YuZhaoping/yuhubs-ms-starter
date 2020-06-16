
SRC_DIR="$SRC_HOME/LB/keepalived/apiserver"


NODE_DEST_HOME="$DEST_HOME"

global_router_id="K8S-APISVR-KEEPALIVED"
vrrp_instance_name="VI-K8S-APISVR"
vrrp_router_id=51

vrrp_virtual_ipaddress="$K8S_APISERVER_VIRTUAL_IP"
NODE_NAMES=${K8S_APISERVER_NODE_NAMES[@]}


source "./_deploy/keepalived/_common.sh"
