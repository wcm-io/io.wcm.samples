@echo off
REM  #%L
REM  wcm.io
REM  %%
REM  Copyright (C) 2020 wcm.io
REM  %%
REM  Licensed under the Apache License, Version 2.0 (the "License");
REM  you may not use this file except in compliance with the License.
REM  You may obtain a copy of the License at
REM
REM       http://www.apache.org/licenses/LICENSE-2.0
REM 
REM  Unless required by applicable law or agreed to in writing, software
REM  distributed under the License is distributed on an "AS IS" BASIS,
REM  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
REM  See the License for the specific language governing permissions and
REM  limitations under the License.
REM  #L%

set AEM_SDK_DISPATCHER_PATH=C:\java\aem-sdk-dispatcher-tools-2.0.21-windows
set AEM_PUBLISH_LOCAL_PORT=45036
set DISPATCHER_LOCAL_PORT=8080

echo.
echo.*** Validate Dispatcher config ***
echo.
del /f/q/s target\dispatcher-out
%AEM_SDK_DISPATCHER_PATH%\bin\validator.exe full -d target\dispatcher-out target\configuration\development\aem-dispatcher
IF %ERRORLEVEL% NEQ 0 ( 
  echo.
  echo.*** Validation failed ***
  echo.
  pause
  exit
)

echo.
echo.*** Start Dispatcher (Docker has to be running) ***
echo.

for /f "delims=[] tokens=2" %%a in ('ping -4 -n 1 %ComputerName% ^| findstr [') do set NetworkIP=%%a
echo Network IP: %NetworkIP%
echo.

call %AEM_SDK_DISPATCHER_PATH%\bin\docker_run.cmd target\dispatcher-out %NetworkIP%:%AEM_PUBLISH_LOCAL_PORT% %DISPATCHER_LOCAL_PORT%
