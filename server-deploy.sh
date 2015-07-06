#!/bin/bash

echo "deploying static resources..."
rm -rf /www/resources/*
cp -r /root/projects/taolijie/src/main/webapp/WEB-INF/views/pc/assets/about /www/resources
cp -r /root/projects/taolijie/src/main/webapp/WEB-INF/views/pc/assets/admin /www/resources
cp -r /root/projects/taolijie/src/main/webapp/WEB-INF/views/pc/assets/fonts /www/resources
cp -r /root/projects/taolijie/src/main/webapp/WEB-INF/views/pc/assets/images /www/resources
cp -r /root/projects/taolijie/src/main/webapp/WEB-INF/views/pc/assets/scripts /www/resources
cp -r /root/projects/taolijie/src/main/webapp/WEB-INF/views/pc/assets/styles /www/resources
echo "done"

echo "deploying war file..."
rm -rf /opt/apache-tomcat-7.0.62/webapps/ROOT/*
cp /root/projects/taolijie/target/taolijie.war /tmp
cd /tmp
unzip taolijie.war
mv WEB-INF/ /opt/apache-tomcat-7.0.62/webapps/ROOT/
mv META-INF/ /opt/apache-tomcat-7.0.62/webapps/ROOT/
rm -rf WEB-INF/
rm -rf META-INF/
rm taolijie.war
echo "done"
