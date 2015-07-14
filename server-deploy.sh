#!/bin/bash


check_success() {
    if [ "$?" != "0" ]; then
        echo "stop build"
        exit 1
    fi
}

echo "building project..."
git pull origin master
check_success

mvn clean install -Dmaven.test.skip=true
check_success

echo "deploying static resources..."
rm -rf /www/resources/*
cp -r /root/projects/taolijie/src/main/webapp/WEB-INF/views/pc/assets/about /www/resources
cp -r /root/projects/taolijie/src/main/webapp/WEB-INF/views/pc/assets/admin /www/resources
cp -r /root/projects/taolijie/src/main/webapp/WEB-INF/views/pc/assets/fonts /www/resources
cp -r /root/projects/taolijie/src/main/webapp/WEB-INF/views/pc/assets/images /www/resources
cp -r /root/projects/taolijie/src/main/webapp/WEB-INF/views/pc/assets/scripts /www/resources
cp -r /root/projects/taolijie/src/main/webapp/WEB-INF/views/pc/assets/styles /www/resources
echo "done"

# shut server down
/opt/apache-tomcat-7.0.62/bin/shutdown.sh
check_success

echo "deploying war file..."
rm -rf /opt/apache-tomcat-7.0.62/webapps/ROOT/*
cp /root/projects/taolijie/target/taolijie.war /tmp
cd /tmp
unzip -q taolijie.war
mv WEB-INF/ /opt/apache-tomcat-7.0.62/webapps/ROOT/
mv META-INF/ /opt/apache-tomcat-7.0.62/webapps/ROOT/
rm -rf WEB-INF/
rm -rf META-INF/
rm taolijie.war

# start server
/opt/apache-tomcat-7.0.62/bin/startup.sh
check_success
echo "done"
