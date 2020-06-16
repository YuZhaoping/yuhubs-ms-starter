#!/usr/bin/env bash

cd $(dirname $0)


PKI_BUILD_HOME="${PKI_BUILD_HOME:-./build/pki}";
PKI_DEPLOY_HOME="${PKI_DEPLOY_HOME:-./build/deploy/pki}";


BUILD_DIR="$PKI_BUILD_HOME"
BUILD_CA_DIR="$BUILD_DIR/CA"

PREFIX="root"


mkdir -p "$BUILD_CA_DIR"

# Generate the CA
if [ ! -f "$BUILD_CA_DIR/$PREFIX-ca.pem" ]; then
  echo "--- Generate the \"${PREFIX}\" CA ---";

  cfssl gencert -initca ./CA/$PREFIX-ca-csr.json | \
  cfssljson -bare "$BUILD_CA_DIR/$PREFIX-ca"
fi

# cfssl-certinfo -cert "$BUILD_CA_DIR/$PREFIX-ca.pem"
