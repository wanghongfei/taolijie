-- 插入角色数据
insert into taolijie.role (rolename, memo) VALUE ('ADMIN', '管理员');
insert into taolijie.role (rolename, memo) VALUE ('STUDENT', '学生');
insert into taolijie.role (rolename, memo) VALUE ('EMPLOYER', '商家');

-- ADMIN账号,密码111111
insert into taolijie.member (username, password) VALUE ('taolijie', '3d4f2bf07dc1be38b20cd6e46949a1071f9d0e3d');
-- 授权
insert into taolijie.member_role (member_id, role_rid) VALUE ( (select id from taolijie.member where username = 'taolijie'), (select rid from taolijie.role where rolename = 'ADMIN') );