#!/usr/bin/env bash

cd $(dirname $0)


PKI_BUILD_HOME="${PKI_BUILD_HOME:-./build/pki}";
PKI_DEPLOY_HOME="${PKI_DEPLOY_HOME:-./build/deploy/pki}";


BUILD_DIR="${PKI_BUILD_HOME}";
DEPLOY_DIR="${PKI_DEPLOY_HOME}/etc";

PREFIX="sa"


mkdir -p "$BUILD_DIR"

# Generate the private key (PKCS#8)
if [ ! -f "$BUILD_DIR/$PREFIX-key.pem" ]; then
  echo "--- Generate the \"service account\" key pair ---";

  openssl genpkey -algorithm RSA -out "$BUILD_DIR/$PREFIX-key.pem" -pkeyopt rsa_keygen_bits:2048
fi


mkdir -p "$DEPLOY_DIR"

if [ ! -f "$DEPLOY_DIR/$PREFIX.key" ]; then
  echo "--- Deploy the \"service account\" key pair ---";

  # Deploy the private key (PKCS#1)
  openssl rsa -in "$BUILD_DIR/$PREFIX-key.pem" \
    -out "$DEPLOY_DIR/$PREFIX.key"

  # Deploy the public key
  openssl rsa -in "$BUILD_DIR/$PREFIX-key.pem" \
    -pubout -out "$DEPLOY_DIR/$PREFIX.pub"
fi
