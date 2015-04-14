# coding=UTF-8
__author__ = 'wanghongfei'

import mysql.connector, sys

CLEAR_DB = False
args = sys.argv[1:]

if len(args) == 1:
    if args[0] == 'clear':
        CLEAR_DB = True
    else:
        print 'wrong parameter!'
        sys.exit(1)

config = {
    'user': 'root',
    'password': '111111',
    'host': 'localhost',
    'database': 'taolijie',
    'raise_on_warnings': True
}


def build_connection(conf):
    connection = mysql.connector.connect(**conf)
    return connection


def close_connection(conn):
    conn.close()


# 清空所有数据
def clear_data():
    sqls = [
        "DELETE FROM member_role",
        "DELETE FROM role",
        "DELETE FROM job_post",
        "DELETE FROM second_hand_post",
        "DELETE FROM job_post_category",
        "DELETE FROM second_hand_post_category",
        "DELETE FROM resume",
        "DELETE FROM academy",
        "DELETE FROM school",
        "DELETE FROM news",
        "DELETE FROM member",
    ]

    for sql in sqls:
        cursor.execute(sql)
        conn.commit()


def insert_role_data(cursor):
    sql = "INSERT INTO role(rolename, memo) VALUES (%s, %s)"
    data = [
        ('ADMIN', '管理员'),
        ('STUDENT', '学生'),
        ('EMPLOYER', '商家')
    ]
    cursor.executemany(sql, data)


def insert_member_data(cursor):
    sql = "INSERT INTO member(username, password, age) VALUES (%(username)s, %(password)s, %(age)s )"
    data = [
        {
            'username': 'wanghongfei',
            'password': '3d4f2bf07dc1be38b20cd6e46949a1071f9d0e3d',
            'age': 22
        },
        {
            'username': 'wangfucheng',
            'password': '3d4f2bf07dc1be38b20cd6e46949a1071f9d0e3d',
            'age': 21
        },
        {
            'username': 'abc',
            'password': '3d4f2bf07dc1be38b20cd6e46949a1071f9d0e3d',
            'age': 18
        }
    ]
    cursor.executemany(sql, data)


def insert_member_role_data(cursor):
    sql = "INSERT INTO member_role(member_id, role_rid) VALUES ( %(member_id)s, %(role_rid)s )"
    data = [
        {
            'member_id': query_member_id('wanghongfei'),
            'role_rid': query_role_id('ADMIN')
        },
        {
            'member_id': query_member_id('wangfucheng'),
            'role_rid': query_role_id('STUDENT')
        },
        {
            'member_id': query_member_id('abc'),
            'role_rid': query_role_id('EMPLOYER')
        }
    ]
    cursor.executemany(sql, data)


def insert_school_data():
    sql = "INSERT INTO school(short_name, full_name, province) VALUES ( %(short_name)s, %(full_name)s,  %(province)s )"
    data = [
        {
            'short_name': '理工大',
            'full_name': '山东理工大学',
            'province': '山东'
        }
    ]
    cursor.executemany(sql, data)


def insert_academy_data():
    sql = "INSERT INTO academy(college_id, short_name, full_name) VALUES (%(college_id)s, %(short_name)s, %(full_name)s )"
    data = [
        {
            'college_id': query_school_id('山东理工大学'),
            'short_name': '计院',
            'full_name': '计算机学院'
        },
        {
            'college_id': query_school_id('山东理工大学'),
            'short_name': '商学院',
            'full_name': '商学院'
        },
        {
            'college_id': query_school_id('山东理工大学'),
            'short_name': '电气学院',
            'full_name': '电气与电子工程学院'
        }
    ]
    cursor.executemany(sql, data)


def insert_news_data():
    sql = "INSERT INTO news(title, content, member_id) VALUE ( %(title)s, %(content)s, %(member_id)s) "
    data = [
        {
            'title': '死人了1',
            'content': '哪里死人了?',
            'member_id': query_member_id('wanghongfei')
        },
        {
            'title': '死人了2',
            'content': '哪里死人了?',
            'member_id': query_member_id('wangfucheng')
        },
        {
            'title': '死人了3',
            'content': '哪里死人了?',
            'member_id': query_member_id('abc')
        }
    ]
    cursor.executemany(sql, data)


def query_school_id(school_name):
    sql = "SELECT id FROM school WHERE full_name = %(full_name)s"
    data = {
        'full_name': school_name
    }
    cursor.execute(sql, data)

    res = cursor.fetchone()
    return res[0]


def query_role_id(rolename):
    sql = "SELECT rid FROM role WHERE rolename = %(rolename)s"
    data = {
        'rolename': rolename
    }
    cursor.execute(sql, data)

    res = cursor.fetchone()
    return res[0]


def query_member_id(username):
    sql = "SELECT id FROM member WHERE username = %(username)s"
    data = {
        'username': username
    }
    cursor.execute(sql, data)

    res = cursor.fetchone()
    return res[0]


def insert_category_data():
    sql = "INSERT INTO job_post_category (name, memo) VALUES (%(name)s, %(memo)s )"
    data = [
        {
            'name': '发传单',
            'memo': ''
        },
        {
            'name': '送快递',
            'memo': ''
        },
        {
            'name': '家政',
            'memo': ''
        }
    ]
    cursor.executemany(sql, data)


    sql = "INSERT INTO second_hand_post_category (name, memo) VALUES (%(name)s, %(memo)s )"
    data = [
        {
            'name': '手机',
            'memo': ''
        },
        {
            'name': '电脑',
            'memo': ''
        },
        {
            'name': '自行车',
            'memo': ''
        }
    ]
    cursor.executemany(sql, data)


def insert_resume_data(cursor, usernames):
    for username in usernames:
        sql = "INSERT INTO resume (member_id, name, gender, access_authority) VALUES ( %(member_id)s, %(name)s, %(gender)s, %(access_authority)s )"
        data = [
            {
                'member_id': query_member_id(username),
                'name': '王鸿飞',
                'gender': '男',
                'access_authority': 'GLOBAL'
            },
            {
                'member_id': query_member_id(username),
                'name': '王鸿飞2',
                'gender': '男',
                'access_authority': 'GLOBAL'
            }
        ]
        cursor.executemany(sql, data)


def query_job_category_id(name):
    sql = "SELECT id FROM job_post_category WHERE name = %(name)s"
    data = ({'name': name})
    cursor.execute(sql, data)

    res = cursor.fetchone()
    return res[0]


def query_sh_category_id(name):
    sql = "SELECT id FROM second_hand_post_category WHERE name = %(name)s"
    data = ({'name': name})
    cursor.execute(sql, data)

    res = cursor.fetchone()
    return res[0]


def insert_sh_data(name_list):
    for name in name_list:
        sql = "INSERT INTO second_hand_post (member_id, second_hand_post_category_id, title, description) VALUES (%(member_id)s, %(second_hand_post_category_id)s, %(title)s, %(description)s) "
        data = [
            {
                'member_id': query_member_id(name),
                'second_hand_post_category_id': query_sh_category_id('自行车'),
                'title': '出售二手山地车',
                'description': '9成新'
            },
            {
                'member_id': query_member_id(name),
                'second_hand_post_category_id': query_sh_category_id('手机'),
                'title': '出售二手iphone',
                'description': '8成新'
            },
            {
                'member_id': query_member_id(name),
                'second_hand_post_category_id': query_sh_category_id('电脑'),
                'title': '出售二手macbook',
                'description': '5成新'
            }
        ]
        cursor.executemany(sql, data)


def insert_job_data(name_list):
    for name in name_list:
        sql = "INSERT INTO job_post (member_id, job_post_category_id, title, introduce) VALUES ( %(member_id)s, %(job_post_category_id)s, %(title)s, %(introduce)s  )"
        data = [
            {
                'member_id': query_member_id(name),
                'job_post_category_id': query_job_category_id('送快递'),
                'title': '送快递',
                'introduce': '998'
            }
        ]
        cursor.executemany(sql, data)

# build connection
conn = build_connection(config)
cursor = conn.cursor()

if CLEAR_DB:
    clear_data()
    print 'done clearing'
    sys.exit(0)

users = ['wanghongfei', 'wangfucheng', 'abc']
# insert data
print 'inserting into role table'
insert_role_data(cursor)
print 'done'

print 'inserting into member table'
insert_member_data(cursor)
print 'done'

print 'inserting into member_role table'
insert_member_role_data(cursor)
print 'done'

print 'inserting into category table'
insert_category_data()
print 'done'

print 'inserting into resume table'
insert_resume_data(cursor, users)
print 'done'

print 'inserting into job table'
insert_job_data(users)
print 'done'

print 'inserting into second_hand table'
insert_sh_data(users)
print 'done'

print 'inserting into school table'
insert_school_data()
print 'done'


print 'inserting into academy table'
insert_academy_data()
print 'done'

print 'inserting into news table'
insert_news_data()
print 'done'


conn.commit()
close_connection(conn)
