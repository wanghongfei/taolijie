# encoding=UTF-8
__author__ = 'whf'

import redis

'''
    该脚本用于将积分配置信息写入到redis中
'''

REDIS_HOST = '127.0.0.1'
REDIS_PASSWORD = '111111'
REDIS_PORT = 6379

CREDITS_LEVEL_KEY = 'CREDITS_LEVEL'
CREDITS_OPERATION_KEY = 'CREDITS_OPERATION'

conn = redis.Redis(REDIS_HOST, REDIS_PORT, password=REDIS_PASSWORD)

# 等级配置
conn.hset(CREDITS_LEVEL_KEY, 'LV0', '0-14')
conn.hset(CREDITS_LEVEL_KEY, 'LV1', '15-29')
conn.hset(CREDITS_LEVEL_KEY, 'LV2', '30-59')
conn.hset(CREDITS_LEVEL_KEY, 'LV3', '60-99')
conn.hset(CREDITS_LEVEL_KEY, 'LV4', '100-149')
conn.hset(CREDITS_LEVEL_KEY, 'LV5', '150-224')
conn.hset(CREDITS_LEVEL_KEY, 'LV6', '225-374')
conn.hset(CREDITS_LEVEL_KEY, 'LV7', '375-549')
conn.hset(CREDITS_LEVEL_KEY, 'LV8', '550-749')
conn.hset(CREDITS_LEVEL_KEY, 'LV9', '750-999')
conn.hset(CREDITS_LEVEL_KEY, 'LV10', '1000-1499')

conn.hset(CREDITS_LEVEL_KEY, 'VIP1', '1500-2499')
conn.hset(CREDITS_LEVEL_KEY, 'VIP2', '2500-3999')
conn.hset(CREDITS_LEVEL_KEY, 'VIP3', '4000-5999')
conn.hset(CREDITS_LEVEL_KEY, 'VIP4', '6000-9999')
conn.hset(CREDITS_LEVEL_KEY, 'VIP5', '10000-204748364')


# 操作积分配置
conn.hset(CREDITS_OPERATION_KEY, 'POST', '2') # 发布信息
conn.hset(CREDITS_OPERATION_KEY, 'REGISTER', '10') # 刚注册
conn.hset(CREDITS_OPERATION_KEY, 'LIKE', '1') # 帖子被赞
conn.hset(CREDITS_OPERATION_KEY, 'REFRESH', '-8') # 刷新简历
conn.hset(CREDITS_OPERATION_KEY, 'FAKE_POST', '-50') # 发布虚假信息
conn.hset(CREDITS_OPERATION_KEY, 'FEEDBACK', '10') # 反馈被采纳
conn.hset(CREDITS_OPERATION_KEY, 'FAV', '1') # 收藏
