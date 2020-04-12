#!/usr/bin/env bash

cd $(dirname $0)


export OUTPUT_DIR=./deploy
export OUTPUT_CA_DIR=$OUTPUT_DIR/ca

export CA_NAME=yuhubs-ecom

export HOST_NAME=auth-server

export PASSWD=yuhubs123


export OUTPUT_HOST_DIR=$OUTPUT_DIR/$HOST_NAME

mkdir -p $OUTPUT_HOST_DIR


if [ ! -f "$OUTPUT_CA_DIR/$CA_NAME.pem" ]; then
  ./build-ca.sh
fi


if [ ! -f "$OUTPUT_HOST_DIR/$HOST_NAME.pem" ]; then

# Build Server Cert
echo "--- Build Server Cert ---"
cfssl gencert -ca=$OUTPUT_CA_DIR/$CA_NAME.pem -ca-key=$OUTPUT_CA_DIR/$CA_NAME-key.pem \
  -config=./ca/ca-config.json -profile="server" \
  ./hosts/$HOST_NAME-csr.json | \
cfssljson -bare $OUTPUT_HOST_DIR/$HOST_NAME

fi

# Verify the Server Cert
echo "--- Verify the Server Cert ---"
openssl verify -CAfile $OUTPUT_DIR/$CA_NAME-ca.crt \
  $OUTPUT_HOST_DIR/$HOST_NAME.pem


# Generate pkcs12 file
if [ ! -f "$OUTPUT_HOST_DIR/$HOST_NAME.p12" ]; then
echo "--- Generate pkcs12 file ---"
openssl pkcs12 -export \
  -in $OUTPUT_HOST_DIR/$HOST_NAME.pem \
  -inkey $OUTPUT_HOST_DIR/$HOST_NAME-key.pem \
  -out $OUTPUT_HOST_DIR/$HOST_NAME.p12 \
  -name $HOST_NAME \
  -password pass:$PASSWD
fi


# Generate keystore
if [ ! -f "$OUTPUT_HOST_DIR/keystore.p12" ]; then
echo "--- Generate keystore ---"
keytool -importkeystore \
  -srckeystore $OUTPUT_HOST_DIR/$HOST_NAME.p12 \
  -srcstoretype PKCS12 \
  -srcstorepass $PASSWD \
  -destkeystore $OUTPUT_HOST_DIR/keystore.p12 \
  -deststoretype PKCS12 \
  -deststorepass $PASSWD\
  -destkeypass $PASSWD
fi
