# encoding=UTF-8
__author__ = 'whf'

import sys
import os
import thread
import time

import BaseHTTPServer
import subprocess
import logging

# 配置logger
FORMAT = "%(asctime)-15s %(message)s"
CUR_TIME = time.strftime('%Y-%m-%d-%H-%M-%S', time.localtime(time.time()))
logging.basicConfig(filename="/data/taolijie/scripts-log/deploy-server-%s.log" % CUR_TIME, format=FORMAT, level=logging.DEBUG)


# 该函数用于执行外部命令来部署工程
def deploy():
    os.chdir('/root/projects/taolijie')
    outcome = subprocess.check_output(('/root/projects/taolijie/server-deploy.sh'))
    logging.info(outcome)

    return outcome


# HTTP请求处理类
class Handler(BaseHTTPServer.BaseHTTPRequestHandler):
    # 处理POST请求
    def do_POST(self):
        logging.info('client addr:%s', self.client_address[0])
        logging.info('client port:%s', self.client_address[1])
        logging.info('method:%s', self.command)

        # 启动新线程执行命令
        thread.start_new_thread(deploy, ())

        self.send_response(200)

    def log_error(self, format, *args):
        logging.error(format, args)


# 启动HTTP Server
server_addr = ('', 10000)
httpd = BaseHTTPServer.HTTPServer(server_addr, Handler)
httpd.serve_forever()
