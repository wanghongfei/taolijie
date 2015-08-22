CREATE TABLE IF NOT EXISTS `taolijie`.`my_collection` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '',
  `created_time` DATETIME NULL COMMENT '收藏时间',
  `member_id` INT NULL COMMENT '收藏者用户id',
  `job_post_id` INT NULL COMMENT '',
  `sh_post_id` INT NULL COMMENT '',
  `resume_id` INT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '',
  INDEX `idx_collection_job_post` (`job_post_id` ASC)  COMMENT '',
  INDEX `idx_collection_sh_post` (`sh_post_id` ASC)  COMMENT '',
  INDEX `idx_collection_resume` (`resume_id` ASC)  COMMENT '',
  INDEX `idx_collection_mem_job` (`member_id` ASC, `job_post_id` ASC)  COMMENT '',
  INDEX `idx_collection_mem_sh` (`member_id` ASC, `sh_post_id` ASC)  COMMENT '',
  INDEX `idx_collection_mem_resume` (`resume_id` ASC, `member_id` ASC)  COMMENT '')
  ENGINE = InnoDB
  COMMENT = '我的收藏关联表'

alter table taolijie.member drop column favorite_job_ids;
alter table taolijie.member drop column favorite_sh_ids;
alter table taolijie.member drop column favorite_resume_ids;
