# encoding=UTF-8
__author__ = 'whf'

'''
该脚本用于抽取文章的关键字，然后生成inverted index
保存到Redis中.

该程序会将job_post表中所有is_indexed字段为false的记录做
inverted index索引
'''

import redis
import mysql.connector

import logging
from wordcut.cut import KeywordGenerator

# 配置日志
FORMAT = "%(asctime)-15s %(message)s: "
logging.basicConfig(filename='gen-index.log', level=logging.INFO, format=FORMAT)

MYSQL_USERNAME = 'root'
MYSQL_PASSWORD = '111111'
MYSQL_HOST = '127.0.0.1'
MYSQL_DATABASE = 'taolijie'

REDIS_HOST = '127.0.0.1'
REDIS_PASSWORD = '111111'
REDIS_PORT = 6379

EACH_FETCH_AMOUNT = 10


def connect_redis(host, port, password):
    return redis.Redis(host, port, password=password)

def connect_mysql(username, password, host, database):
    mysql_config = {
        'user': username,
        'password': password,
        'host': host,
        'database': database,
        'raise_on_warnings': True
    }

    return mysql.connector.connect(**mysql_config)


word_gen = KeywordGenerator('stop-words.txt')

# 连接redis
redis_conn = connect_redis(REDIS_HOST, REDIS_PORT, REDIS_PASSWORD)
# 连接数据库
mysql_conn = connect_mysql(MYSQL_USERNAME, MYSQL_PASSWORD, MYSQL_HOST, MYSQL_DATABASE)
mysql_cursor = mysql_conn.cursor()

# 读取兼职数据
sql = '''
    SELECT
        id, title, job_detail AS content
    FROM
        job_post
    WHERE
        is_indexed = false
    LIMIT
        0, %s
    ''' % EACH_FETCH_AMOUNT
mysql_cursor.execute(sql)
# 遍历结果集
job_id_list = []
for (id, title, content) in mysql_cursor:
    if content:
        # 提取内容关键字
        keywords = word_gen.gen_keywords(content)
        # 放入redis的SET中
        # 关键字为SET名, id为value
        for word in keywords:
            redis_conn.expire(word, 60 * 10) # 10 minunies
            redis_conn.sadd(word, id)
        job_id_list.append(id)
mysql_cursor.close()

# 当id list不为空时才执行, 否则SQL会报错
if job_id_list:
    # 将完成索引创建的兼职信息标记为true
    mysql_cursor = mysql_conn.cursor()
    sql = '''
        UPDATE
            job_post
        SET
            is_indexed = true
        WHERE
            id IN %s
        ''' % str(tuple(job_id_list))
    print str(tuple(job_id_list))
    mysql_cursor.execute(sql)
    print '%s rows of job post updated' % mysql_cursor.rowcount
    mysql_cursor.close()
else:
    print 'There is no data'

mysql_conn.commit()
mysql_conn.close()
