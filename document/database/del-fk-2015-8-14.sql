alter table taolijie.resume_post_rel drop foreign key fk_resume_post_rel_job_post1;
alter table taolijie.resume_post_rel drop foreign key fk_resume_post_rel_member1;
alter table taolijie.resume_post_rel drop foreign key fk_resume_post_rel_resume1;

alter table taolijie.news add column place varchar(45);