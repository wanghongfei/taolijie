# encoding=UTF-8
__author__ = 'whf'

'''
该脚本用于抽取文章的关键字，然后生成inverted index
保存到Redis中.
'''

import redis
import mysql.connector
from wordcut.cut import KeywordGenerator

class JobPost:
    def __init__(self):
        self.title = ''
        self.content = ''


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
redis_conn = connect_redis('127.0.0.1', 6379, '111111')
# 连接数据库
mysql_conn = connect_mysql('root', '111111', '127.0.0.1', 'taolijie')
mysql_cursor = mysql_conn.cursor()

# 读取兼职数据
sql = 'SELECT id, title, job_detail AS content FROM job_post LIMIT 0, 10'
mysql_cursor.execute(sql)
# 遍历结果集
for (id, title, content) in mysql_cursor:
    if content:
        # 提取内容关键字
        keywords = word_gen.gen_keywords(content)
        # 放入redis的SET中
        # 关键字为SET名, id为value
        for word in keywords:
            print 'added: SET = %s, value = %s' % (word, id)
            redis_conn.expire(word, 60 * 10) # 10 minunies
            redis_conn.sadd(word, id)

mysql_conn.close
