alter table taolijie.resume add column spare_time varchar(50);

-- 修改job_post表的work_place字段长度
alter TABLE taolijie.job_post drop COLUMN work_place;
alter table taolijie.job_post add column work_place varchar(256);

-- 修改job_description长度
alter TABLE taolijie.job_post drop COLUMN job_description;
alter table taolijie.job_post add column job_description varchar(1000);

-- 修改job_detail长度
alter TABLE taolijie.job_post drop COLUMN job_detail;
alter table taolijie.job_post add column job_detail varchar(1000);
