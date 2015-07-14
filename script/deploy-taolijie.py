# encoding=UTF-8
__author__ = 'whf'

import os
import sys
import shutil

import subprocess

if len(sys.argv) == 2:
    if sys.argv[1] == 'pull':
        print '[INFO] pulling code from github'
        outcome = subprocess.check_output(('git', 'pull', 'origin', 'master'), stderr=subprocess.STDOUT)
        print outcome
    else:
        print 'invalid argument. try pull'
        sys.exit(0)

def exe_cmd(args):
    try:
        outcome = subprocess.check_output(args)
    except subprocess.CalledProcessError as e:
        print '[ERROR] failed!!!!'
        print 'output:\n' + e.output
        sys.exit(1)

    return outcome



print '[INFO] rebuild project'
os.chdir('/root/projects/taolijie')
outcome = exe_cmd(('mvn', 'clean', 'install', '-Dmaven.test.skip=true'))
print outcome



print '[INFO] deploy static resources'
STATIC_RESOURCE_PATH = '/www/resources/'
for dir_name in os.listdir(STATIC_RESOURCE_PATH):
    shutil.rmtree(dir_name)
ASSET_PATH = '/root/projects/taolijie/src/main/webapp/WEB-INF/views/pc/assets'
shutil.copytree(ASSET_PATH.join('/about'), STATIC_RESOURCE_PATH + 'about')
shutil.copytree(ASSET_PATH.join('/admin'), STATIC_RESOURCE_PATH + 'admin')
shutil.copytree(ASSET_PATH.join('/fonts'), STATIC_RESOURCE_PATH + 'fonts')
shutil.copytree(ASSET_PATH.join('/images'), STATIC_RESOURCE_PATH + 'images')
shutil.copytree(ASSET_PATH.join('/scripts'), STATIC_RESOURCE_PATH + 'scripts')
shutil.copytree(ASSET_PATH.join('/styles'), STATIC_RESOURCE_PATH + 'styles')


print '[INFO] shut tomcat down'
TOMCAT_HOME = os.getenv('TOMCAT_HOME')
TOMCAT_PATH = os.getenv('TOMCAT_HOME') + '/bin'
outcome = exe_cmd((TOMCAT_PATH + 'shutdown.sh'))
print outcome

print '[INFO] deploy WAR file'
shutil.rmtree(TOMCAT_HOME + '/webapps/ROOT')
shutil.copy('/root/projects/taolijie/target/taolijie.war', '/tmp')
os.chdir('/tmp')
subprocess.call(('unzip', '-q', 'taolijie.war'))
shutil.copytree('WEB-INF', TOMCAT_HOME + '/webapps/ROOT')
shutil.copytree('META-INF', TOMCAT_HOME + '/webapps/ROOT')

print '[INFO] start tomcat'
outcome = exe_cmd((TOMCAT_PATH + 'startup.sh'))
print outcome

print '[INFO] clean up'
shutil.rmtree('WEB-INF')
shutil.rmtree('META-INF')
os.remove('taolijie.war')

print '[INFO] all done.'
