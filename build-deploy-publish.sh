#!/bin/sh
# Call with "help" parameter to display syntax information

SLING_URL="http://localhost:4503"
CONGA_NODE="aem-publish"

if [[ $0 == *":\\"* ]]; then
  DISPLAY_PAUSE_MESSAGE=true
fi

./build-deploy.sh --sling.url=${SLING_URL} --conga.node=${CONGA_NODE} --display.pause.message=${DISPLAY_PAUSE_MESSAGE} "$@"
