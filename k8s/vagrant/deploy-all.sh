#!/usr/bin/env bash

cd $(dirname $0)

export K8S_HOME=$(readlink -m "../")


source "$K8S_HOME/DEPLOY.variables"


VAGRANT_DEPLOY_HOME="../build/deploy"

VAGRANT_SRC_HOME="."


VAGRANT_BOX_NAME="\"yuhubs.k8s.vbox\/${K8S_VAGRANT_BOX_BASENAME}\""


convert_array() {
  local -n args=$1

  local result="\["
  local count=0
  for e in ${args[@]}; do
    if [ $count -gt 0 ]; then
      result="${result}, "
    fi
    result="${result}\"$e\""
    count=$(($count+1))
  done
  result="${result}\]"

  echo "$result"
}

cp_script_files() {
  local src_dir=$1
  local dest_dir=$2

  local script_files=($src_dir/*.sh)

  for file in ${script_files[@]}; do
    file=${file##*/}

    cp -f "$src_dir/$file" "$dest_dir/$file"
    chmod a+x "$dest_dir/$file"
  done
}


categories=("Services" "Ingresses")

for cat in ${categories[@]}; do

  VAGRANT_SRC_DIR="${VAGRANT_SRC_HOME}/$cat"
  VAGRANT_DEST_DIR="${VAGRANT_DEPLOY_HOME}/$cat"

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

  NODE_NAMES_ARRAY=$(convert_array NODE_NAMES)
  NODE_IPS_ARRAY=$(convert_array NODE_IPS)


  mkdir -p "$VAGRANT_DEST_DIR"


  sed \
    -e "s/\[\"<node_names>\"\]/${NODE_NAMES_ARRAY}/" \
    -e "s/\[\"<node_ips>\"\]/${NODE_IPS_ARRAY}/" \
    -e "s/\"<vagrant_box_name>\"/${VAGRANT_BOX_NAME}/" \
    "$VAGRANT_SRC_DIR/Vagrantfile.tmpl" \
    >"$VAGRANT_DEST_DIR/Vagrantfile"


  cp_script_files \
    "$VAGRANT_SRC_DIR" \
    "$VAGRANT_DEST_DIR/.."

done
