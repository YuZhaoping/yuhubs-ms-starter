#!/usr/bin/env bash

cd $(dirname $0)

export YUHUBS_MS_HTML_LOCATION="file:$(pwd)/deploy/html/public/"

echo $YUHUBS_MS_HTML_LOCATION
