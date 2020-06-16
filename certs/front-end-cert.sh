#!/usr/bin/env bash

cd $(dirname $0)


export OUTPUT_DIR=./deploy
export OUTPUT_CA_DIR=$OUTPUT_DIR/CA

export CA_NAME=yuhubs-ecom

export HOST_NAME=front-end


export OUTPUT_HOST_DIR=$OUTPUT_DIR/$HOST_NAME

mkdir -p $OUTPUT_HOST_DIR


if [ ! -f "$OUTPUT_CA_DIR/$CA_NAME.pem" ]; then
  ./build-ca.sh
fi


if [ ! -f "$OUTPUT_HOST_DIR/$HOST_NAME.pem" ]; then

# Build Server Cert
echo "--- Build Server Cert ---"
cfssl gencert -ca=$OUTPUT_CA_DIR/$CA_NAME.pem -ca-key=$OUTPUT_CA_DIR/$CA_NAME-key.pem \
  -config=./CA/ca-config.json -profile="server" \
  ./hosts/$HOST_NAME-csr.json | \
cfssljson -bare $OUTPUT_HOST_DIR/$HOST_NAME

fi

# Verify the Server Cert
echo "--- Verify the Server Cert ---"
openssl verify -CAfile $OUTPUT_DIR/$CA_NAME-ca.crt \
  $OUTPUT_HOST_DIR/$HOST_NAME.pem
