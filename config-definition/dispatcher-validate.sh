#!/bin/bash

AEM_SDK_DISPATCHER_PATH=/c/java/aem-sdk-dispatcher-tools-2.0.21-windows

if [[ $0 == *":\\"* ]]; then
  DISPLAY_PAUSE_MESSAGE=true
fi

pause_message() {
  if [ "$DISPLAY_PAUSE_MESSAGE" = true ]; then
    echo ""
    read -n1 -r -p "Press any key to continue..."
  fi
}

rm -rf target/dispatcher-out
${AEM_SDK_DISPATCHER_PATH}/bin/validator.exe full -d target/dispatcher-out target/configuration/development/aem-dispatcher

if [ "$?" -ne "0" ]; then
  echo "*** Validation failed ***"
  pause_message
  exit 1
fi

pause_message
