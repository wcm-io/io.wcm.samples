#!/bin/sh

# defaultconfig
sling_url="http://localhost:4502"
sling_user="admin"
sling_password="admin"
sling_params=""

# set parameter variables before run
init()
{
sling_params="-Dsling.url=$sling_url -Dsling.user=$sling_user -Dsling.password=$sling_password"
other_params=$4
}

####

# run modes
deploy_only()
{
    init
    deploy_artifacts
}

default_build()
{
    motd
    init
    clean_install
    deploy_artifacts
}

#####

motd()
{
echo "********************************************************************"
echo ""
echo " Cleans and installs all modules"
echo " Uploads and installs application complete packages, config and sample content"
echo ""
echo " Destination: $sling_url"
echo ""
echo "********************************************************************"
}

####

clean_install()
{
echo ""
echo "*** Build artifacts ***"
echo ""

mvn $sling_params $other_params -Pfast clean install eclipse:eclipse

if [ "$?" -ne "0" ]; then
  exit_with_error "*** Build artifacts FAILED ***"
fi
}

#####

deploy_artifacts()
{

echo ""
echo "*** Deploy AEM packages  ***"
echo ""

cd config-definition
mvn $sling_params -Pdeploy-packages conga-aem:package-install

if [ "$?" -ne "0" ]; then
  exit_with_error "*** Deploying config packages FAILED ***"
fi

cd ../

}

#####

# Display a pause message (only when the script was executed via double-click on windows)
pause_on_windows_dblclick() {
  if [[ $0 == *":\\"* ]]; then
    echo ""
    read -n1 -r -p "Press any key to continue..."
  fi
}

# Displays error message and exit the script with error code
exit_with_error() {
  echo ""
  echo "$1" 1>&2
  echo ""
  pause_on_windows_dblclick
  exit 1
}

# check params and run
if [ "$1" != "" ]
then
    if [ "$1" = "deploy_only" ]
    then
            # commandlineconfig
            sling_url=$2
            sling_user=$3
            sling_password=$4
            deploy_only
    else
            # commandlineconfig
            sling_url=$1
            sling_user=$2
            sling_password=$3
      shift 3
      other_params=$@
            default_build
    fi
else
    default_build
fi


echo ""
echo "*** Build complete ***"
echo ""
pause_on_windows_dblclick
