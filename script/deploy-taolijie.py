# encoding=UTF-8
__author__ = 'whf'

import os
import sys
import shutil

import subprocess

if sys.argv:
    if sys.argv[1] == 'pull':
        print '[INFO] pulling code from github'
        outcome = subprocess.check_output(('git', 'pull', 'origin', 'master'), stderr=subprocess.STDOUT)
        print outcome
    else:
        print 'invalid argument. try pull'
        sys.exit(0)


print '[INFO] rebuild project'
try:
    outcome = subprocess.check_output(('mvn', 'clean', 'install', '-Dmaven.test.skip=true'))
except subprocess.CalledProcessError as e:
    print '[ERROR] rebuild project failed!!!!'
    print 'output:\n' + e.output



print '[INFO] deploy static resources'
STATIC_RESOURCE_PATH = '/www/resources'
for dir_name in os.listdir(STATIC_RESOURCE_PATH):
    shutil.rmtree(dir_name)
ASSET_PATH = '/root/projects/taolijie/src/main/webapp/WEB-INF/views/pc/assets'
shutil.copytree(ASSET_PATH.join('/about'), STATIC_RESOURCE_PATH)
shutil.copytree(ASSET_PATH.join('/admin'), STATIC_RESOURCE_PATH)
shutil.copytree(ASSET_PATH.join('/fonts'), STATIC_RESOURCE_PATH)
shutil.copytree(ASSET_PATH.join('/images'), STATIC_RESOURCE_PATH)
shutil.copytree(ASSET_PATH.join('/scripts'), STATIC_RESOURCE_PATH)
shutil.copytree(ASSET_PATH.join('/styles'), STATIC_RESOURCE_PATH)


print '[INFO] shut tomcat down'
TOMCAT_HOME = os.getenv('TOMCAT_HOME')
TOMCAT_PATH = os.getenv('TOMCAT_HOME').join('/bin')
try:
    outcome = subprocess.check_output((TOMCAT_PATH.join('shutdown.sh')))
except subprocess.CalledProcessError as e:
    print '[ERROR] shut down failed!!'
    print 'message:\n'.join(e.output)


print '[INFO] deploy WAR file'
shutil.rmtree(TOMCAT_HOME.join('/webapps/ROOT'))
shutil.copy('/root/projects/taolijie/target/taolijie.war', '/tmp')
os.chdir('/tmp')
subprocess.call(('unzip', 'taolijie.war'))
shutil.copytree('WEB-INF', TOMCAT_HOME.join('/webapps/ROOT'))
shutil.copytree('META-INF', TOMCAT_HOME.join('/webapps/ROOT'))

print '[INFO] start tomcat'
try:
    outcome = subprocess.check_output((TOMCAT_PATH.join('startup.sh')))
except subprocess.CalledProcessError as e:
    print '[ERROR] starting Tomcat failed!!'
    print 'message:\n'.join(e.output)

print '[INFO] clean up'
shutil.rmtree('WEB-INF')
shutil.rmtree('META-INF')
os.remove('taolijie.war')

print '[INFO] all done.'
