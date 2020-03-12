#!/usr/bin/env bash

cd $(dirname $0)


export OUTPUT_DIR=./deploy
export OUTPUT_CA_DIR=$OUTPUT_DIR/ca

mkdir -p $OUTPUT_CA_DIR

export PREFIX=yuhubs


# Build the CAs
if [ ! -f "$OUTPUT_CA_DIR/$PREFIX-ecom.pem" ]; then

# Build Root CA
echo "--- Build Root CA ---"
cfssl gencert -initca ./ca/ca-csr.json | \
cfssljson -bare $OUTPUT_CA_DIR/$PREFIX-ca


# Build Ecom CA (intermediate)
echo "--- Build Ecom CA (intermediate) ---"
cfssl gencert -initca ./ca/ecom-csr.json | \
cfssljson -bare $OUTPUT_CA_DIR/$PREFIX-ecom

cfssl sign -ca=$OUTPUT_CA_DIR/$PREFIX-ca.pem -ca-key=$OUTPUT_CA_DIR/$PREFIX-ca-key.pem \
  -config=./ca/ca-config.json -profile="intermediate_ca" \
  $OUTPUT_CA_DIR/$PREFIX-ecom.csr | \
cfssljson -bare $OUTPUT_CA_DIR/$PREFIX-ecom

fi

# Verify the Ecom CA
echo "--- Verify the Ecom CA ---"
openssl verify -CAfile $OUTPUT_CA_DIR/$PREFIX-ca.pem \
  $OUTPUT_CA_DIR/$PREFIX-ecom.pem


# Bundle the Root & Ecom CAs
if [ ! -f "$OUTPUT_DIR/$PREFIX-ecom-ca.crt" ]; then
echo "--- Bundle the Root & Ecom CAs ---"
mkbundle -f $OUTPUT_DIR/$PREFIX-ecom-ca.crt \
  $OUTPUT_CA_DIR/$PREFIX-ca.pem $OUTPUT_CA_DIR/$PREFIX-ecom.pem
fi

# Verify the bundled CAs
echo "--- Verify the bundled CAs ---"
openssl verify -CAfile $OUTPUT_DIR/yuhubs-ecom-ca.crt \
  $OUTPUT_CA_DIR/$PREFIX-ecom.pem
