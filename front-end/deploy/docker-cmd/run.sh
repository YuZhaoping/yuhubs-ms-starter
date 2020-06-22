#!/usr/bin/env bash

cd $(dirname $0)

export YUHUBS_MS_HTML_LOCATION=`../../bin/export-html-location.sh`
echo ""
echo "export YUHUBS_MS_HTML_LOCATION=\"${YUHUBS_MS_HTML_LOCATION}\""
echo ""

docker run -d --name ecom-front-end \
  --network=ecom-backend --net-alias=front-end -p 8080:80 -p 8443:443 \
  -v "${YUHUBS_MS_HTML_LOCATION}":/usr/share/nginx/html:ro \
  com.yuhubs.ecom/front-end:0.0.1
