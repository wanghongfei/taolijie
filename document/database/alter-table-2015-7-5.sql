alter table taolijie.private_notification drop column time;
alter table taolijie.private_notification add column time DATETIME;

-- 添加兼职的工作地域
alter table taolijie.job_post add column work_region varchar(40)
