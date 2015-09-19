@echo off
call mvn package -Dvault.unpack=true wcmio-content-package:download
cd ..
pause
