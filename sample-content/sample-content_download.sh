#!/bin/sh

mvn package -Dvault.unpack=true wcmio-content-package:download

read -n1 -r -p "Press any key to continue..." key
