alter table taolijie.private_notification add column post_id int;
alter table taolijie.private_notification add column post_type varchar(10);

alter table taolijie.member add column province varchar(40);
alter table taolijie.member add column city varchar(20);
alter table taolijie.member add column region varchar(20);
alter table taolijie.member add column college_name varchar(20);
alter table taolijie.member add column major varchar(20);