#!/usr/bin/env bash

cd $(dirname $0)


PKI_BUILD_HOME="${PKI_BUILD_HOME:-./build/pki}";
PKI_DEPLOY_HOME="${PKI_DEPLOY_HOME:-./build/deploy/pki}";

K8S_HOME="${K8S_HOME:-..}";


BUILD_DIR="$PKI_BUILD_HOME"
BUILD_CA_DIR="$BUILD_DIR/CA"

ROOT_CA_NAME="root-ca"
CA_NAME="kube-ca"

DEPLOY_HOME="$PKI_DEPLOY_HOME/etc"
DEPLOY_DIR="${DEPLOY_HOME}"

CERTS_DIR=./certs
BUILD_CERTS_HOME="$BUILD_DIR/certs/kube"


if [ ! -f "$BUILD_CA_DIR/$CA_NAME.pem" ]; then
  ./kube-ca.sh
fi


source "$K8S_HOME/DEPLOY.variables"


APISERVER_NODE_HOSTS=""
for node_ip in ${K8S_APISERVER_NODE_IPS[@]}; do
  APISERVER_NODE_HOSTS="${APISERVER_NODE_HOSTS}\"${node_ip}\", "
done

APISERVER_VIRTUAL_IP="\"${K8S_APISERVER_VIRTUAL_IP}\", "

CLUSTER_IP="\"${K8S_APISERVER_CLUSTER_IP}\", "


SERVICE_DNS_DOMAIN="${K8S_SERVICE_DNS_DOMAIN:-cluster.local}"
SERVICE_DNS_SUBDOMAIN="${SERVICE_DNS_DOMAIN%.*}"


CERT_NAMES=("apiserver" "apiserver-kubelet-client")
CERT_KIND="client"

for cert_name in ${CERT_NAMES[@]}; do
  echo ">>>>>>>>";

  BUILD_CERT_DIR="$BUILD_CERTS_HOME/${cert_name}";

  mkdir -p "$BUILD_CERT_DIR";


  if [ "${cert_name}" = "apiserver" ]; then
    sed -e "s/\"<apiserver-nodes-hostname-Host_IPs>\",/${APISERVER_NODE_HOSTS}/" \
        -e "s/\"<apiserver-advertise_IP-other_name_IPs>\",/${APISERVER_VIRTUAL_IP}/" \
        -e "s/\"<cluster_IP>\",/${CLUSTER_IP}/" \
        -e "s/<service_dns_subdomain>/${SERVICE_DNS_SUBDOMAIN}/" \
        -e "s/<service_dns_domain>/${SERVICE_DNS_DOMAIN}/" \
      "$CERTS_DIR/${cert_name}-csr.tmpl" >"$BUILD_CERT_DIR/${cert_name}-csr.json";
    CERT_KIND="server";
  else
    cp -f "$CERTS_DIR/${cert_name}-csr.json" "$BUILD_CERT_DIR/";
    CERT_KIND="client";
  fi

  # --- Generate Cert ---

  if [ ! -f "$BUILD_CERT_DIR/${cert_name}.pem" ]; then
    echo "--- Generate the kube: \"${cert_name}\" cert ---";

    cfssl gencert -ca="$BUILD_CA_DIR/$CA_NAME.pem" -ca-key="$BUILD_CA_DIR/$CA_NAME-key.pem" \
      -config=./CA/ca-config.json -profile="${CERT_KIND}" \
      "$BUILD_CERT_DIR/${cert_name}-csr.json" | \
    cfssljson -bare "$BUILD_CERT_DIR/${cert_name}";
  fi

  # --- Verify Cert ---

  echo "--- Verify the kube: \"${cert_name}\" cert ---";

#  openssl verify -CAfile "$BUILD_CA_DIR/$ROOT_CA_NAME.pem" \
#    -untrusted "$BUILD_CA_DIR/$CA_NAME.pem" \
#
  openssl verify -CAfile "${PKI_DEPLOY_HOME}/etc/ca.crt" \
    "$BUILD_CERT_DIR/${cert_name}.pem";

  # --- Deploy Cert ---

  mkdir -p "$DEPLOY_DIR";

  if [ ! -f "$DEPLOY_DIR/${cert_name}.key" ]; then
    echo "--- Deploy the kube: \"${cert_name}\" cert ---";

    cp -vf "$BUILD_CERT_DIR/${cert_name}-key.pem" \
      "$DEPLOY_DIR/${cert_name}.key";

    mkbundle -f "$DEPLOY_DIR/${cert_name}.crt" \
      "$BUILD_CERT_DIR/${cert_name}.pem";
  fi

  echo "<<<<<<<<";
done
