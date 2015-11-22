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
pipe.hset('sysconf', 'top_fee', 5)
pipe.hset('sysconf', 'tag_fee', 5)
pipe.hset('sysconf', 'question_fee', 0.10)
pipe.hset('sysconf', 'survey_fee', 0.10)
pipe.hset('sysconf', 'max_emp_withdraw_day', 1000)
pipe.hset('sysconf', 'max_stu_withdraw_day', 100)
pipe.hset('sysconf', 'flush_fee', 0.5)

pipe.hset('sysconf', 'rsa_pub_key', 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDK4FAnbvP1imoKtKkUVEzO2uZpckeKfbFc9krXeQUvKiORvHOUE/j9UZIHZ4B/WG5MWL+zIdaJOzbIpkmNM/G6iyodFRraT6SabdSbzQRDv+p8yJKr9clfyWXKmijaRAE3nvhgEVKDLw2BGRay5s1OZaP5Gpr1P7ADPvEdFyhw7QIDAQAB')
pipe.hset('sysconf', 'rsa_pri_key', 'MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAMrgUCdu8/WKagq0qRRUTM7a5mlyR4p9sVz2Std5BS8qI5G8c5QT+P1RkgdngH9YbkxYv7Mh1ok7NsimSY0z8bqLKh0VGtpPpJpt1JvNBEO/6nzIkqv1yV/JZcqaKNpEATee+GARUoMvDYEZFrLmzU5lo/kamvU/sAM+8R0XKHDtAgMBAAECgYB25uTTV7nvrMHdqGh4G7gwfCcSogG/707mFwtiIfGh5OaX4YUzwkjp3sbhRA4RlLiAUnt391sPbv76tkrhURJYoI5TTyjwNCPhHtiEKGwdDIEd2PueMoXlA8dPs5ERpa3qAZQnQNZhghLXM1iEt2EcmGPvhv2kLuuDHrBdRcF1AQJBAPYh0laLZvPloOChRN6Sx/7HjqPWwteZRZ3ibArNFsP3joFxyH7mWWlA+HUR2Sk86nuip+THGUV4o//ImLbqmQ0CQQDTAojMXlIsVMR0MjaS1rNZY5dP5lGAzTYIFIj4QmOL+/WopgFI1K4pbIzdciREt3XVLiw6IBW+yzaaRXTb839hAkEA3Ry3M+5Z18OzSfk2wGjGaDjoCIG3Wi4kX1j4TJ+/0t7DU8BfKLlRG4A1dB5Lo4NI9zbBc4H1Tva5BeE0aCbKxQJBAKkXsNRFCYio7T1IN9932gVZ/5lxsgKLcC8Nda2sVeWJpZTeYs0gUei0KjYTD+lZMz4AYES8DXD1m+4IltrjyEECQQCllCOBagmFe7u+/O96OVauwZ+gfmfXYfFPS4poqs50dleFyn4GvKEsdC6gQC+9zUjWornc9BqfxfxugyyFybSM')

pipe.hset('conf:qiniu', 'ak', 'jiCcIJ426S8uHSN4fqL5OGjEmqSdENWTnk4Ze8I6')
pipe.hset('conf:qiniu', 'sk', 'rshYBtwtUWYCG5mcsFsHBnhz1vrgpaLKDymeNqiB')
pipe.hset('conf:qiniu', 'bucket', 'taolijie')


pipe.hset('conf:alipay', 'PID', '2088021861615600')
pipe.hset('conf:alipay', 'app_pay_service', 'mobile.securitypay.pay')
pipe.hset('conf:alipay', 'charset', 'utf-8')
pipe.hset('conf:alipay', 'sign_type', 'RSA')
pipe.hset('conf:alipay', 'notify_url', 'http://120.24.218.56')
pipe.hset('conf:alipay', 'acc', 'taolijie@vip.sina.com')

pipe.hset('conf:wechat', 'appid', 'wxb5b95b3df3bc8a0b')
pipe.hset('conf:wechat', 'mchid', '1279805401')
pipe.hset('conf:wechat', 'secret', 'd4624c36b6795d1d99dcf0547af5443d')
pipe.hset('conf:wechat', 'certi', 'apiclient_cert.p12')
pipe.hset('conf:wechat', 'certi_path', '/Users/whf/projects/taolijie/apiclient_cert.p12')

print pipe.execute()

