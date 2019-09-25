#!/bin/bash
# #%L
#  wcm.io
#  %%
#  Copyright (C) 2017 - 2019 wcm.io
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

# Call with "help" parameter to display syntax information

# defaults
MAVEN_PROFILES="fast"
SLING_URL=""
SLING_USER=""
SLING_PASSWORD=""
CONGA_ENVIRONMENT="development"
CONGA_NODE="aem-author"
JVM_ARGS=""

# display pause message only when script was executed via double-click on windows
if [[ $0 == *":\\"* ]]; then
  DISPLAY_PAUSE_MESSAGE=true
fi
BUILD=false
DEPLOY=false
HELP=false
DEFAULT_COMMANDS=true

####

help_message_exit() {
  echo ""
  echo "  Syntax <parameters> <commands>"
  echo ""
  echo "  Parameters:"
  echo "    --maven.profiles=${MAVEN_PROFILES}       or -P${MAVEN_PROFILES}"
  echo "    --sling.url=${SLING_URL}                 or -Dsling.url=${SLING_URL}"
  echo "    --sling.user=${SLING_USER}               or -Dsling.user=${SLING_USER}"
  echo "    --sling.password=${SLING_PASSWORD}       or -Dsling.password=${SLING_PASSWORD}"
  echo "    --conga.environment=${CONGA_ENVIRONMENT} or -Dconga.environment=${CONGA_ENVIRONMENT}"
  echo "    --conga.node=${CONGA_NODE}               or -Dconga.node=${CONGA_NODE}"
  echo "    --jvm.args=${JVM_ARGS}                   or -Djvm.args=${JVM_ARGS}"
  echo ""
  echo "  Commands:"
  echo "    build  - Clean and install maven project"
  echo "    deploy - Deploy packages to AEM instance"
  echo "    help   - display this help message"
  echo ""

  exit 0
}

parse_parameters() {

  for i in "$@"
  do
  case $i in
      --maven\.profiles=*|-P*)
      MAVEN_PROFILES="${i#*=}"
      shift # past argument=value
      ;;
      --sling\.url=*|-Dsling\.url=*)
      SLING_URL="${i#*=}"
      shift # past argument=value
      ;;
      --sling\.user=*|-Dsling\.user=*)
      SLING_USER="${i#*=}"
      shift # past argument=value
      ;;
      --sling\.password=*|-Dsling\.password=*)
      SLING_PASSWORD="${i#*=}"
      shift # past argument=value
      ;;
      --conga\.environment=*|-Dconga\.environment=*)
      CONGA_ENVIRONMENT="${i#*=}"
      shift # past argument=value
      ;;
      --conga\.node=*|-Dconga\.node=*)
      CONGA_NODE="${i#*=}"
      shift # past argument=value
      ;;
      --jvm\.args=*|-Djvm\.args=*)
      JVM_ARGS="${i#*=}"
      shift # past argument=value
      ;;
      --display\.pause\.message=*|-Ddisplay\.pause\.message=*)
      DISPLAY_PAUSE_MESSAGE="${i#*=}"
      shift # past argument with no value
      ;;
      build)
      BUILD=true
      DEFAULT_COMMANDS=false
      shift # past argument with no value
      ;;
      deploy)
      DEPLOY=true
      DEFAULT_COMMANDS=false
      ;;
      help)
      HELP=true
      DEFAULT_COMMANDS=false
      shift # past argument with no value
      ;;
      *)
            # unknown option
      ;;
  esac
  done

  if [ "$DEFAULT_COMMANDS" = true ]; then
    BUILD=true
    DEPLOY=true
  fi
}

welcome_message() {
  echo -e "********************************************************************\e[96m"
  if ([ "$BUILD" = true ] && [ "$DEPLOY" = true ]) || [ "$HELP" = true ]; then
    echo "   ___ _   _ ___ _    ___      _     ___  ___ ___ _    _____   __"
    echo "  | _ ) | | |_ _| |  |   \   _| |_  |   \| __| _ \ |  / _ \ \ / /"
    echo "  | _ \ |_| || || |__| |) | |_   _| | |) | _||  _/ |_| (_) \ V /"
    echo "  |___/\___/|___|____|___/    |_|   |___/|___|_| |____\___/ |_|"
  elif [ "$BUILD" = true ]; then
    echo "   ___ _   _ ___ _    ___ "
    echo "  | _ ) | | |_ _| |  |   \\"
    echo "  | _ \ |_| || || |__| |) |"
    echo "  |___/\___/|___|____|___/"
  elif [ "$DEPLOY" = true ]; then
    echo "   ___  ___ ___ _    _____   __"
    echo "  |   \| __| _ \ |  / _ \ \ / /"
    echo "  | |) | _||  _/ |_| (_) \ V /"
    echo "  |___/|___|_| |____\___/ |_|"
  fi
  echo -e "\e[0m"
  echo -e "  Destination: \e[1m${CONGA_NODE}\e[0m (${MAVEN_PROFILES})"
  echo ""
  echo "********************************************************************"
}

completion_message() {
  ELAPSED_TIME=$(($SECONDS - $START_TIME))
  TOTAL_TIME="($(($ELAPSED_TIME/60)):$(printf "%02d" $(($ELAPSED_TIME%60))) min)"

  echo ""
  if [ "$BUILD" = true ] && [ "$DEPLOY" = true ]; then
    echo -e "*** \e[1mBuild+Deploy complete\e[0m $TOTAL_TIME ***"
  elif [ "$BUILD" = true ]; then
    echo -e "*** \e[1mBuild complete\e[0m $TOTAL_TIME ***"
  elif [ "$DEPLOY" = true ]; then
    echo -e "*** \e[1mDeploy complete\e[0m $TOTAL_TIME ***"
  fi
  echo ""

  pause_message
}

####

execute_build() {
  echo ""
  echo -e "*** \e[1mBuild application\e[0m ***"
  echo ""

  MAVEN_ARGS=""
  if [ -n "$JVM_ARGS" ]; then
    MAVEN_ARGS+="${JVM_ARGS} "
  fi
  if [ -n "${MAVEN_PROFILES}" ]; then
    MAVEN_ARGS+="--activate-profiles ${MAVEN_PROFILES} "
  fi
  if [ -n "${CONGA_ENVIRONMENT}" ]; then
    MAVEN_ARGS+="-Dconga.environments=${CONGA_ENVIRONMENT} "
  fi

  mvn $MAVEN_ARGS clean install eclipse:eclipse

  if [ "$?" -ne "0" ]; then
    exit_with_error "*** BUILD FAILED ***"
  fi
}

####

execute_deploy() {
  echo ""
  echo -e "*** \e[1mDeploy to AEM\e[0m ***"
  echo ""

  MAVEN_ARGS=""
  if [ -n "$JVM_ARGS" ]; then
    MAVEN_ARGS+="${JVM_ARGS} "
  fi
  if [ -n "${MAVEN_PROFILES}" ]; then
    MAVEN_ARGS+="--activate-profiles=${MAVEN_PROFILES} "
  fi
  if [ -n "${CONGA_ENVIRONMENT}" ] && [ -n "${CONGA_NODE}" ]; then
    MAVEN_ARGS+="-Dconga.environments=${CONGA_ENVIRONMENT} -Dconga.nodeDirectory=target/configuration/${CONGA_ENVIRONMENT}/${CONGA_NODE} "
  fi
  if [ -n "${SLING_URL}" ]; then
    MAVEN_ARGS+="-Dsling.url=${SLING_URL} "
  fi
  if [ -n "${SLING_USER}" ]; then
    MAVEN_ARGS+="-Dsling.user=${SLING_USER} "
  fi
  if [ -n "${SLING_PASSWORD}" ]; then
    MAVEN_ARGS+="-Dsling.password=${SLING_PASSWORD} "
  fi

  mvn $MAVEN_ARGS -f config-definition conga-aem:package-install

  if [ "$?" -ne "0" ]; then
    exit_with_error "*** DEPLOY FAILED ***"
  fi

}

####

# Display a pause message (only when the script was executed via double-click on windows)
pause_message() {
  if [ "$DISPLAY_PAUSE_MESSAGE" = true ]; then
    read -n1 -r -p "Press any key to continue..."
  fi
}

# Displays error message and exit the script with error code
exit_with_error() {
  echo ""
  echo -e "\e[91m$1\e[0m" 1>&2
  echo ""
  pause_message
  exit 1
}

####

START_TIME=$SECONDS

parse_parameters "$@"
welcome_message
if [ "$HELP" = true ]; then
  help_message_exit
fi
if [ "$BUILD" = true ]; then
  execute_build
fi
if [ "$DEPLOY" = true ]; then
  execute_deploy
fi
completion_message

