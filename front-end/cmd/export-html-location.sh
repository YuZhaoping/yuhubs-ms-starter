#!/usr/bin/env bash

cd $(dirname $0) && cd ../

export YUHUBS_MS_HTML_LOCATION="$(pwd)/deploy/html"

echo $YUHUBS_MS_HTML_LOCATION
