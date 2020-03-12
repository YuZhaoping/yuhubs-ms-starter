#!/usr/bin/env bash

mkdir -p /tmp/cfssl
cd /tmp/cfssl

# https://github.com/cloudflare/cfssl/releases

export CFSSL_VER=1.4.1

export CFSSL_DOWNLOAD_URL=https://github.com/cloudflare/cfssl/releases/download/v$CFSSL_VER


if [ ! -f "/usr/local/bin/cfssl" ]; then
wget -O cfssl_linux_amd64 $CFSSL_DOWNLOAD_URL/cfssl_${CFSSL_VER}_linux_amd64
sudo mv -v cfssl_linux_amd64 /usr/local/bin/cfssl
chmod +x /usr/local/bin/cfssl
fi

if [ ! -f "/usr/local/bin/cfssljson" ]; then
wget -O cfssljson_linux_amd64 $CFSSL_DOWNLOAD_URL/cfssljson_${CFSSL_VER}_linux_amd64
sudo mv -v cfssljson_linux_amd64 /usr/local/bin/cfssljson
chmod +x /usr/local/bin/cfssljson
fi

if [ ! -f "/usr/local/bin/cfssl-certinfo" ]; then
wget -O cfssl-certinfo_linux_amd64 $CFSSL_DOWNLOAD_URL/cfssl-certinfo_${CFSSL_VER}_linux_amd64
sudo mv -v cfssl-certinfo_linux_amd64 /usr/local/bin/cfssl-certinfo
chmod +x /usr/local/bin/cfssl-certinfo
fi


if [ ! -f "/usr/local/bin/cfssl-bundle" ]; then
wget -O cfssl-bundle_linux_amd64 $CFSSL_DOWNLOAD_URL/cfssl-bundle_${CFSSL_VER}_linux_amd64
sudo mv -v cfssl-bundle_linux_amd64 /usr/local/bin/cfssl-bundle
chmod +x /usr/local/bin/cfssl-bundle
fi

if [ ! -f "/usr/local/bin/mkbundle" ]; then
wget -O mkbundle_linux_amd64 $CFSSL_DOWNLOAD_URL/mkbundle_${CFSSL_VER}_linux_amd64
sudo mv -v mkbundle_linux_amd64 /usr/local/bin/mkbundle
chmod +x /usr/local/bin/mkbundle
fi
