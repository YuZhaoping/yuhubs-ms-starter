#!/usr/bin/env bash

cd $(dirname $0)

export K8S_HOME=$(readlink -m "../")


source "$K8S_HOME/DEPLOY.variables"


PROVISION_DEPLOY_HOME="../build/deploy"

PROVISION_SRC_HOME="."


cp_script_files() {
  local src_dir=$1
  local dest_dir=$2

  local subdir=$3

  if [ ! "x$subdir" = "x" ]; then
    subdir="$subdir/"
  fi


  local script_files=($src_dir/*.sh)

  for file in ${script_files[@]}; do
    file=${file##*/}

    echo "--- deploy: [${subdir}${file}] ---"

    cp -f "$src_dir/$file" "$dest_dir/$file"
    chmod a+x "$dest_dir/$file"
  done
}


categories=("Services" "Ingresses")

for cat in ${categories[@]}; do

  PROVISION_SRC_DIR="${PROVISION_SRC_HOME}/$cat"
  PROVISION_DEST_DIR="${PROVISION_DEPLOY_HOME}/$cat"

  case "$cat" in
    "Ingresses")
      NODE_NAMES=("${K8S_INGRESS_NODE_NAMES[@]}");
      NODE_IPS=("${K8S_INGRESS_NODE_IPS[@]}");
      ;;
    *)
      NODE_NAMES=("${K8S_SERVICE_NODE_NAMES[@]}");
      NODE_IPS=("${K8S_SERVICE_NODE_IPS[@]}");
      ;;
  esac


  mkdir -p "$PROVISION_DEST_DIR"


  if [ -f "$PROVISION_DEST_DIR/hosts" ]; then
    truncate --size=0 "$PROVISION_DEST_DIR/hosts"
  fi

  echo -e "\n# The following lines are K8S nodes" >>"$PROVISION_DEST_DIR/hosts"

  for node_index in "${!NODE_NAMES[@]}"; do
    node_name=${NODE_NAMES[$node_index]}

    lowercased_node_name=$(echo "$node_name" | tr '[:upper:]' '[:lower:]')

    node_ip=${NODE_IPS[$node_index]}

    echo -e "${node_ip}\t\t${lowercased_node_name}\t${node_name}" \
      >>"$PROVISION_DEST_DIR/hosts"
  done


  cp -f "$K8S_HOME/DEPLOY.variables" "$PROVISION_DEST_DIR"


  cp_script_files \
    "$PROVISION_SRC_DIR" \
    "$PROVISION_DEST_DIR" \
    ""


  declare -a subdirs
  readarray -t subdirs < <(find "$PROVISION_SRC_DIR" -maxdepth 2 -type d -printf '%P\n')
  for subdir in ${subdirs[@]}; do
    mkdir -p "$PROVISION_DEST_DIR/$subdir"

    cp_script_files \
      "$PROVISION_SRC_DIR/$subdir" \
      "$PROVISION_DEST_DIR/$subdir" \
      "$subdir"
  done

done
