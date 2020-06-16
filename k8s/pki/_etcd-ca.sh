#!/usr/bin/env bash

cd $(dirname $0)


PKI_BUILD_HOME="${PKI_BUILD_HOME:-./build/pki}";
PKI_DEPLOY_HOME="${PKI_DEPLOY_HOME:-./build/deploy/pki}";


BUILD_DIR="$PKI_BUILD_HOME"
BUILD_CA_DIR="$BUILD_DIR/CA"

ROOT_CA_NAME="root-ca"

DEPLOY_DIR="$PKI_DEPLOY_HOME/etc/etcd"

PREFIX="etcd"


if [ ! -f "$BUILD_CA_DIR/$ROOT_CA_NAME.pem" ]; then
  ./root-ca.sh
fi


mkdir -p "$BUILD_CA_DIR"

# Generate the CA
if [ ! -f "$BUILD_CA_DIR/$PREFIX-ca.pem" ]; then
  echo "--- Generate the \"${PREFIX}\" CA ---";

  cfssl gencert -initca ./CA/$PREFIX-ca-csr.json | \
  cfssljson -bare "$BUILD_CA_DIR/$PREFIX-ca"

  cfssl sign -ca="$BUILD_CA_DIR/$ROOT_CA_NAME.pem" -ca-key="$BUILD_CA_DIR/$ROOT_CA_NAME-key.pem" \
    -config=./CA/ca-config.json -profile="intermediate_ca" \
    "$BUILD_CA_DIR/$PREFIX-ca.csr" | \
  cfssljson -bare "$BUILD_CA_DIR/$PREFIX-ca"
fi

# cfssl-certinfo -cert "$BUILD_CA_DIR/$PREFIX-ca.pem"


mkdir -p "$DEPLOY_DIR"

if [ ! -f "$DEPLOY_DIR/ca.key" ]; then
  echo "--- Deploy the \"${PREFIX}\" CA ---";

  cp -vf "$BUILD_CA_DIR/$PREFIX-ca-key.pem" \
    "$DEPLOY_DIR/ca.key"

  mkbundle -f "$DEPLOY_DIR/ca.crt" \
    "$BUILD_CA_DIR/$ROOT_CA_NAME.pem" \
    "$BUILD_CA_DIR/$PREFIX-ca.pem"
fi
