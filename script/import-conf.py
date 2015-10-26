# encoding=UTF-8
__author__ = 'whf'


import redis

REDIS_HOST = '127.0.0.1'
REDIS_PASSWORD = '111111'
REDIS_PORT = 6379

# 连接redis
conn = redis.Redis(REDIS_HOST, REDIS_PORT, password=REDIS_PASSWORD)

# start a pipe
pipe = conn.pipeline()

pipe.hset('sysconf', 'quest_fee_rate', 10)
pipe.hset('sysconf', 'audit_fee', 1)
pipe.hset('sysconf', 'top_fee', 0.10)
pipe.hset('sysconf', 'tag_fee', 0.10)
pipe.hset('sysconf', 'question_fee', 0.10)
pipe.hset('sysconf', 'survey_fee', 0.10)
pipe.hset('sysconf', 'max_emp_withdraw_day', 1000)
pipe.hset('sysconf', 'max_stu_withdraw_day', 100)
pipe.hset('sysconf', 'flush_fee', 0.5)
print pipe.execute()

