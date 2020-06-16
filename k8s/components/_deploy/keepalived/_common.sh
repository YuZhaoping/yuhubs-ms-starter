
vrrp_state="MASTER"
vrrp_interface="eth1"
vrrp_priority=120

node_index=0
for node_name in ${NODE_NAMES[@]}; do

  echo "--- deploy: [${node_name}] keepalived ---"


  DEST_DIR="$NODE_DEST_HOME/$node_name/keepalived"

  mkdir -p "$DEST_DIR"

  if [ $node_index -gt 0 ]; then
    vrrp_state="BACKUP"
  fi

  sed \
    -e "s/<global_router_id>/$global_router_id/" \
    -e "s/<vrrp_instance_name>/$vrrp_instance_name/" \
    -e "s/<vrrp_state>/$vrrp_state/" \
    -e "s/<vrrp_interface>/$vrrp_interface/" \
    -e "s/<vrrp_router_id>/$vrrp_router_id/" \
    -e "s/<vrrp_priority>/$vrrp_priority/" \
    -e "s/<vrrp_virtual_ipaddress>/$vrrp_virtual_ipaddress/" \
    "$SRC_DIR/keepalived.conf" \
    >"$DEST_DIR/keepalived.conf"

  vrrp_priority=$(($vrrp_priority-10))
  node_index=$(($node_index+1))
done
