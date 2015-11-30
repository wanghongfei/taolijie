alter TABLE taolijie.quest drop column province_id;
alter TABLE taolijie.quest drop column city_id;
alter TABLE taolijie.quest drop column region_id;
alter TABLE taolijie.quest drop column college_id;
alter TABLE taolijie.quest drop column school_id;

alter table taolijie.quest add column is_target_all bool default false;

CREATE TABLE IF NOT EXISTS `taolijie`.`off_quest` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '',
  `mem_id` INT NOT NULL COMMENT '任务发布者id',
  `username` VARCHAR(45) NULL COMMENT '冗余字段, 任务发布者用户名',
  `title` VARCHAR(45) NOT NULL COMMENT '',
  `cate_id` INT NOT NULL COMMENT '分类id',
  `cate_name` VARCHAR(20) NULL COMMENT '冗余字段, 分类名',
  `award` DECIMAL(5,2) NOT NULL COMMENT '',
  `created_time` DATETIME NOT NULL COMMENT '',
  `work_time` DATETIME NULL COMMENT '任务执行时间',
  `work_place` VARCHAR(60) NULL COMMENT '',
  `description` VARCHAR(200) NULL COMMENT '任务描述',
  `contact_phone` VARCHAR(45) NULL COMMENT '联系人手机号',
  `status` VARCHAR(2) NOT NULL COMMENT '任务状态. 00:发布成功 01:已经被领取 02:已经下线',
  `province_id` INT NULL COMMENT '',
  `city_id` INT NULL COMMENT '',
  `region_id` INT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '',
  INDEX `idx_mem_id` (`mem_id` ASC)  COMMENT '',
  INDEX `idx_cate_id` (`cate_id` ASC)  COMMENT '',
  INDEX `idx_award` (`award` ASC)  COMMENT '',
  INDEX `idx_created_time` (`created_time` ASC)  COMMENT '',
  INDEX `idx_status` (`status` ASC)  COMMENT '',
  INDEX `idx_region_id` (`region_id` ASC)  COMMENT '',
  INDEX `idx_city_id` (`city_id` ASC)  COMMENT '')
  ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `taolijie`.`off_cate` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '',
  `name` VARCHAR(20) NULL COMMENT '分类名',
  `memo` VARCHAR(80) NULL COMMENT '',
  `theme_color` VARCHAR(10) NULL COMMENT '',
  `level` INT NULL COMMENT '层级',
  PRIMARY KEY (`id`)  COMMENT '',
  INDEX `idx_name` (`name` ASC)  COMMENT '')
  ENGINE = InnoDB
  COMMENT = '轻兼职分类表';