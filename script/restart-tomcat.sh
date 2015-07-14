#!/bin/bash

if [ "$TOMCAT_HOME" -eq "" ]; then
    echo "TOMCAT_HOME not specified!"
    exit 1
fi

cd $TOMCAT_HOME/bin
./shutdown.sh
./startup.sh
