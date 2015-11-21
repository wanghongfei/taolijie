#!/bin/bash


check_success() {
    if [ "$?" != "0" ]; then
        echo "stop build"
        exit 1
    fi
}

STATIC_RESOURCE='/www/resources'
STATIC_ASSETS='/root/projects/taolijie/src/main/webapp/WEB-INF/views/pc/assets'

echo "building project..."
git pull coding master
check_success

mvn clean install -Dmaven.test.skip=true
check_success

echo "deploying static resources..."
#rm -rf /www/resources/*
cp -r ${STATIC_ASSETS}/about ${STATIC_RESOURCE}
cp -r ${STATIC_ASSETS}/admin ${STATIC_RESOURCE}
cp -r ${STATIC_ASSETS}/fonts ${STATIC_RESOURCE}
cp -r ${STATIC_ASSETS}/images ${STATIC_RESOURCE}
cp -r ${STATIC_ASSETS}/scripts ${STATIC_RESOURCE}
cp -r ${STATIC_ASSETS}/styles ${STATIC_RESOURCE}
echo "done"

# shut server down
$TOMCAT_HOME/bin/shutdown.sh
check_success

echo "deploying war file..."
rm -rf $TOMCAT_HOME/webapps/ROOT/*
cp /root/projects/taolijie/target/taolijie.war /tmp
cd /tmp
unzip -q taolijie.war
mv WEB-INF/ $TOMCAT_HOME/webapps/ROOT/
mv META-INF/ $TOMCAT_HOME/webapps/ROOT/
rm -rf WEB-INF/
rm -rf META-INF/
rm taolijie.war

# start server
su web
$TOMCAT_HOME/bin/startup.sh
check_success
echo "done"
