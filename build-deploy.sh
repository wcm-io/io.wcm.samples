#!/bin/sh
# Call with "help" parameter to display syntax information

# defaults
SLING_URL="http://localhost:4502"
SLING_USER="admin"
SLING_PASSWORD="admin"
CONGA_ENVIRONMENT="development"
CONGA_NODE="aem-author"
JVM_ARGS=""
BUILD=false
DEPLOY=false
HELP=false
DEFAULT_COMMANDS=true

# display pause message only when script was executed via double-click on windows
if [[ $0 == *":\\"* ]]; then
  DISPLAY_PAUSE_MESSAGE=true
fi

####

parse_parameters() {  

  for i in "$@"
  do
  case $i in
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
  echo "********************************************************************"
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
  echo ""
  echo "  Destination: ${SLING_URL} (${CONGA_NODE})"
  echo ""
  echo "********************************************************************"
}

help_message() {
  echo ""
  echo "  Syntax <parameters> <commands>"
  echo ""
  echo "  Parameters:"
  echo "    --sling.url=${SLING_URL}"
  echo "    --sling.user=${SLING_USER}"
  echo "    --sling.password=${SLING_PASSWORD}"
  echo "    --conga.environment=${CONGA_ENVIRONMENT}"
  echo "    --conga.node=${CONGA_NODE}"
  echo "    --jvm.args=${JVM_ARGS}"
  echo ""
  echo "  Commands:"
  echo "    build  - Clean and install maven project"
  echo "    deploy - Deploy packages to AEM instance"
  echo "    help   - display this help message"
  echo ""

  exit 0
}

completion_message() {
  echo ""
  if [ "$BUILD" = true ] && [ "$DEPLOY" = true ]; then
    echo "*** Build+Deploy complete ***"
  elif [ "$BUILD" = true ]; then
    echo "*** Build complete ***"
  elif [ "$DEPLOY" = true ]; then
    echo "*** Deploy complete ***"
  fi
  echo ""

  pause_message
}

####

execute_build() {
  echo ""
  echo "*** Build application ***"
  echo ""

  mvn ${JVM_ARGS} \
      -Dconga.environments=${CONGA_ENVIRONMENT}  \
      -Pfast clean install eclipse:eclipse
  
  if [ "$?" -ne "0" ]; then
    exit_with_error "*** BUILD FAILED ***"
  fi
}

####

execute_deploy() {
  echo ""
  echo "*** Deploy to AEM ***"
  echo ""

  mvn -f config-definition \
      ${JVM_ARGS} \
      -Dconga.environments=${CONGA_ENVIRONMENT} \
      -Dconga.nodeDirectory=target/configuration/${CONGA_ENVIRONMENT}/${CONGA_NODE} \
      -Dsling.url=${SLING_URL} \
      -Dsling.user=${SLING_USER} \
      -Dsling.password=${SLING_PASSWORD} \
      conga-aem:package-install

  if [ "$?" -ne "0" ]; then
    exit_with_error "*** DEPLOY FAILED ***"
  fi

}

####

# Display a pause message (only when the script was executed via double-click on windows)
pause_message() {
  if [ "$DISPLAY_PAUSE_MESSAGE" = true ]; then
    echo ""
    read -n1 -r -p "Press any key to continue..."
  fi
}

# Displays error message and exit the script with error code
exit_with_error() {
  echo ""
  echo "$1" 1>&2
  echo ""
  pause_message
  exit 1
}

####

parse_parameters "$@"
welcome_message
if [ "$HELP" = true ]; then
  help_message
fi
if [ "$BUILD" = true ]; then
  execute_build
fi
if [ "$DEPLOY" = true ]; then
  execute_deploy
fi
completion_message
