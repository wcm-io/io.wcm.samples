#!/bin/sh

CONGA_ENVIRONMENT="development"
CONGA_NODE="aem-author"

if [[ $0 == *":\\"* ]]; then
  DISPLAY_PAUSE_MESSAGE=true
fi

mvn -Dconga.nodeDirectory=target/configuration/${CONGA_ENVIRONMENT}/${CONGA_NODE} clean install conga-aem:package-install

if [ "$DISPLAY_PAUSE_MESSAGE" = true ]; then
  echo ""
  read -n1 -r -p "Press any key to continue..."
fi
