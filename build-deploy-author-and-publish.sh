#!/bin/bash
# #%L
#  wcm.io
#  %%
#  Copyright (C) 2021 wcm.io
#  %%
#  Licensed under the Apache License, Version 2.0 (the "License");
#  you may not use this file except in compliance with the License.
#  You may obtain a copy of the License at
#
#       http://www.apache.org/licenses/LICENSE-2.0
#
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
#  limitations under the License.
#  #L%

# Builds the application, deploys it to local author, and then deploys it to local publish as well (without building it again)
# Deployment to author and publish runs in parallel

# Display a pause message (only when the script was executed via double-click on windows)
pause_message() {
  if [ "$DISPLAY_PAUSE_MESSAGE_WRAPPER" = true ]; then
    echo ""
    read -n1 -r -p "Press any key to continue..."
  fi
}

if [[ $0 == *":\\"* ]]; then
  DISPLAY_PAUSE_MESSAGE_WRAPPER=true
fi

# Build application
./build-deploy.sh build --maven.profiles=fast "$@"
if [ "$?" -ne "0" ]; then
  pause_message
  exit $?
fi

# Deploy to author (in parallel)
./build-deploy.sh deploy --maven.profiles=fast --conga.node=aem-author "$@" &

# Deploy to publish (in parallel)
./build-deploy.sh deploy --maven.profiles=fast,publish --conga.node=aem-publish "$@" &

wait
pause_message
