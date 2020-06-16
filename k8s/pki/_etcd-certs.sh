#!/usr/bin/env bash

cd $(dirname $0)


PKI_BUILD_HOME="${PKI_BUILD_HOME:-./build/pki}";
PKI_DEPLOY_HOME="${PKI_DEPLOY_HOME:-./build/deploy/pki}";

K8S_HOME="${K8S_HOME:-..}";


BUILD_DIR="$PKI_BUILD_HOME"
BUILD_CA_DIR="$BUILD_DIR/CA"

ROOT_CA_NAME="root-ca"
CA_NAME="etcd-ca"

DEPLOY_HOME="$PKI_DEPLOY_HOME/etc"
DEPLOY_DIR="${DEPLOY_HOME}/etcd"

CERTS_DIR=./certs/etcd
BUILD_CERTS_HOME="$BUILD_DIR/certs/etcd"


if [ ! -f "$BUILD_CA_DIR/$CA_NAME.pem" ]; then
  ./etcd-ca.sh
fi


source "$K8S_HOME/DEPLOY.variables"


ETCD_NODE_HOSTS=""
for node_ip in ${K8S_ETCD_NODE_IPS[@]}; do
  ETCD_NODE_HOSTS="${ETCD_NODE_HOSTS}\"${node_ip}\", "
done


CERT_NAMES=("apiserver-etcd-client" "healthcheck-client" "peer" "server")
CERT_KIND="client"

for cert_name in ${CERT_NAMES[@]}; do
  echo ">>>>>>>>";

  case "${cert_name}" in
    "peer" | "server")
      CERT_KIND="peer";
      DEPLOY_DIR="${DEPLOY_HOME}/etcd";
      ;;
    "apiserver-etcd-client")
      CERT_KIND="client";
      DEPLOY_DIR="${DEPLOY_HOME}";
      ;;
    *)
      CERT_KIND="client";
      DEPLOY_DIR="${DEPLOY_HOME}/etcd";
      ;;
  esac

  BUILD_CERT_DIR="$BUILD_CERTS_HOME/${cert_name}";

  mkdir -p "$BUILD_CERT_DIR";

  if [ ! "${CERT_KIND}" = "client" ]; then
    sed -e "s/\"<etcd-nodes-hostname-Host_IPs>\",/${ETCD_NODE_HOSTS}/" \
      "$CERTS_DIR/${cert_name}-csr.tmpl" >"$BUILD_CERT_DIR/${cert_name}-csr.json";
  else
    cp -f "$CERTS_DIR/${cert_name}-csr.json" "$BUILD_CERT_DIR/";
  fi

  # --- Generate Cert ---

  if [ ! -f "$BUILD_CERT_DIR/${cert_name}.pem" ]; then
    echo "--- Generate the etcd: \"${cert_name}\" cert ---";

    cfssl gencert -ca="$BUILD_CA_DIR/$CA_NAME.pem" -ca-key="$BUILD_CA_DIR/$CA_NAME-key.pem" \
      -config=./CA/ca-config.json -profile="${CERT_KIND}" \
      "$BUILD_CERT_DIR/${cert_name}-csr.json" | \
    cfssljson -bare "$BUILD_CERT_DIR/${cert_name}";
  fi

  # --- Verify Cert ---

  echo "--- Verify the etcd: \"${cert_name}\" cert ---";

#  openssl verify -CAfile "$BUILD_CA_DIR/$ROOT_CA_NAME.pem" \
#    -untrusted "$BUILD_CA_DIR/$CA_NAME.pem" \
#
  openssl verify -CAfile "$PKI_DEPLOY_HOME/etc/etcd/ca.crt" \
    "$BUILD_CERT_DIR/${cert_name}.pem";

  # --- Deploy Cert ---

  mkdir -p "$DEPLOY_DIR";

  if [ ! -f "$DEPLOY_DIR/${cert_name}.key" ]; then
    echo "--- Deploy the etcd: \"${cert_name}\" cert ---";

    cp -vf "$BUILD_CERT_DIR/${cert_name}-key.pem" \
      "$DEPLOY_DIR/${cert_name}.key";

    mkbundle -f "$DEPLOY_DIR/${cert_name}.crt" \
      "$BUILD_CERT_DIR/${cert_name}.pem";
  fi

  echo "<<<<<<<<";
done
