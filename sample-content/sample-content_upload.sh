#!/bin/sh

mvn clean package wcmio-content-package:install

read -n1 -r -p "Press any key to continue..." key
