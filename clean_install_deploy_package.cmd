@echo off

if "%1"=="" goto defaultconfig:
goto commandlineconfig

:defaultconfig
rem ---------------- configuration ---------------------

set sling_url=http://localhost:4502
set sling_user=admin
set sling_password=admin

rem ----------------------------------------------------
goto execute

:commandlineconfig
set sling_url=%1
set sling_user=%2
set sling_password=%3
goto execute

:execute

set sling_params=-Dsling.url=%sling_url% -Dsling.user=%sling_user% -Dsling.password=%sling_password%

echo.**********************************************************************************
echo.
echo. Cleans and installs all modules
echo. Uploads and installs application complete packages, config and sample content
echo.
echo. Destination: %sling_url%
echo.
echo.**********************************************************************************

echo.
echo.*** Build artifacts ***
echo.
call mvn -Pfast %sling_params% clean install eclipse:eclipse
if errorlevel 1 goto error

echo.
echo.*** Deploy complete packages ***
echo.
cd complete
call mvn %sling_params% wcmio-content-package:install
if errorlevel 1 goto error
cd ..

echo.
echo.*** Deploy config and samplecontent packages ***
echo.
 
cd config-definition
call mvn %sling_params% -Pdeploy-config-packages wcmio-content-package:install
if errorlevel 1 goto error
cd ..
 
cd sample-content
call mvn %sling_params% wcmio-content-package:install
if errorlevel 1 goto error
cd ..

goto end

:error
echo.************************
echo.     BUILD FAILED
echo.************************

:end
if "%1"=="" pause
