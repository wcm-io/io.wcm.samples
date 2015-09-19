@echo off
call mvn clean package wcmio-content-package:install
cd ..
pause
