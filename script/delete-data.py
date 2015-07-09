# coding=UTF-8
__author__ = 'whf'
# 该脚本用于删除兼职表和二手表中
# delete字段为true的记录

import sys
import argparse
import mysql.connector
import logging
import time

# 设置命令行参数格式
parser = argparse.ArgumentParser()
parser.add_argument('--port', dest='port', help='连接端口')
parser.add_argument('--host', dest='host', help='连接地址')
parser.add_argument('--username', dest='username', help='用户名')
parser.add_argument('--password', dest='password', help='密码')

args = parser.parse_args()

HOST = '127.0.0.1'
PORT = 3306
USERNAME = 'root'
PASSWORD = ''

# 读取命令行参数
if args.port:
    PORT = int(args.port)
if args.host:
    HOST = args.host
if args.username:
    USERNAME = args.username
if args.password:
    PASSWORD = args.password


# configure logger
cur_time = time.strftime('%Y-%m-%d-%H-%M-%S', time.localtime(time.time()))
logging.basicConfig(filename='deletion-%s.log' % cur_time, level=logging.DEBUG)


print '连接参数:'
print 'port = %s' % PORT
print 'host = %s' % HOST
print 'username = %s' % USERNAME
print 'password = %s' % PASSWORD


conn_config = {
    'user': USERNAME,
    'password': PASSWORD,
    'host': HOST,
    'database': 'taolijie',
    'raise_on_warnings': True
}

# establish connection
conn = None
try:
    conn = mysql.connector.connect(**conn_config)
except Exception as e:
    print e
    print '连接失败'
    sys.exit(1)

print '连接MySQL成功'


cursor = conn.cursor()

# 选择出所有被删除的兼职信息的id
sql = 'SELECT id, title FROM job_post WHERE deleted = true'
cursor.execute(sql)

# 将id保存到list中
logging.info('兼职数据')
job_id_list = []
for (job_id, job_title) in cursor:
    logging.info('id = %s, title = %s', job_id, job_title)
    job_id_list.append(job_id)


def delete_data(table_name, id_list):
    sql = 'DELETE FROM ' + table_name + ' WHERE id = %s'
    count = 0

    for id in id_list:
        sql_data = [id]
        cursor.execute(sql, sql_data)
        count += 1
        print '成功删除数据 id = %d, 来自%s表' % job_id, table_name

    return count


# 删除这些兼职数据
count = delete_data('job_post', job_id_list)
logging.info('删除了%d条兼职记录' % count)
print '删除了%d条兼职记录' % count


# 选择出所有被删除的二手信息
logging.info('二手数据')
sql = 'SELECT id, title FROM second_hand_post WHERE deleted = true'
cursor.execute(sql)

sh_id_list = []
for (sh_id, sh_title) in cursor:
    logging.info('id = %d, title = %d', sh_id, sh_title)
    sh_id_list.append(sh_id)

# 删除这些二手数据
count = delete_data('second_hand_post', sh_id_list)
logging.info('删除了%d条二手记录' % count)
print '删除了%d条二手记录' % count


conn.commit()
conn.close()
