#!/usr/bin/env bash

cd $(dirname $0)


PKI_BUILD_HOME="${PKI_BUILD_HOME:-./build/pki}";
PKI_DEPLOY_HOME="${PKI_DEPLOY_HOME:-./build/deploy/pki}";


BUILD_DIR="$PKI_BUILD_HOME"
BUILD_CA_DIR="$BUILD_DIR/CA"

ROOT_CA_NAME="root-ca"
CA_NAME="front-proxy-ca"

DEPLOY_DIR="$PKI_DEPLOY_HOME/etc"

CERTS_DIR=./certs
BUILD_CERTS_HOME="$BUILD_DIR/certs/kube"


if [ ! -f "$BUILD_CA_DIR/$CA_NAME.pem" ]; then
  ./front-proxy-ca.sh
fi


CERT_NAME="front-proxy-client"
CERT_KIND="client"

BUILD_CERT_DIR="$BUILD_CERTS_HOME/$CERT_NAME"

mkdir -p "${BUILD_CERT_DIR}"

# --- Generate Cert ---

if [ ! -f "$BUILD_CERT_DIR/$CERT_NAME.pem" ]; then
  cp -vf "$CERTS_DIR/$CERT_NAME-csr.json" "$BUILD_CERT_DIR/"

  echo "--- Generate the \"${CERT_NAME}\" cert ---"

  cfssl gencert -ca="$BUILD_CA_DIR/$CA_NAME.pem" -ca-key="$BUILD_CA_DIR/$CA_NAME-key.pem" \
    -config=./CA/ca-config.json -profile="$CERT_KIND" \
    "$BUILD_CERT_DIR/$CERT_NAME-csr.json" | \
  cfssljson -bare "$BUILD_CERT_DIR/$CERT_NAME"
fi

# --- Verify Cert ---

echo "--- Verify the \"${CERT_NAME}\" cert ---"

#openssl verify -CAfile "$BUILD_CA_DIR/$ROOT_CA_NAME.pem" \
#  -untrusted "$BUILD_CA_DIR/$CA_NAME.pem" \
#
openssl verify -CAfile "${PKI_DEPLOY_HOME}/etc/$CA_NAME.crt" \
  "$BUILD_CERT_DIR/$CERT_NAME.pem"


# --- Deploy Cert ---

mkdir -p "$DEPLOY_DIR"

if [ ! -f "$DEPLOY_DIR/$CERT_NAME.key" ]; then
  echo "--- Deploy the \"${CERT_NAME}\" cert ---";

  cp -vf "$BUILD_CERT_DIR/$CERT_NAME-key.pem" \
    "$DEPLOY_DIR/$CERT_NAME.key"

  mkbundle -f "$DEPLOY_DIR/$CERT_NAME.crt" \
    "$BUILD_CERT_DIR/$CERT_NAME.pem"
fi
