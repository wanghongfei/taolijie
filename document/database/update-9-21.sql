alter table taolijie.quest add column province_id int;
alter table taolijie.quest add column city_id int;
alter table taolijie.quest add column region_id int;
alter table taolijie.quest add column college_id int;
alter table taolijie.quest add column school_id int;

alter table taolijie.withdraw_apply add column acc_id int not null;
alter table taolijie.quest add column final_award decimal(9,2);
alter table taolijie.quest add column member_id int not null;


CREATE TABLE IF NOT EXISTS `taolijie`.`dict_province` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '',
  `name` VARCHAR(30) NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '')
  ENGINE = InnoDB
  COMMENT = '省表';

CREATE TABLE IF NOT EXISTS `taolijie`.`dict_city` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '',
  `name` VARCHAR(20) NULL COMMENT '',
  `province_id` INT NULL COMMENT '所属于的省，没有则是直辖市',
  PRIMARY KEY (`id`)  COMMENT '')
  ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `taolijie`.`dict_region` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '',
  `name` VARCHAR(30) NULL COMMENT '',
  `province_id` INT NULL COMMENT '所属的省',
  `city_id` INT NULL COMMENT '所属的市',
  PRIMARY KEY (`id`)  COMMENT '')
  ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `taolijie`.`dict_college` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '',
  `full_name` VARCHAR(45) NULL COMMENT '',
  `short_name` VARCHAR(10) NULL COMMENT '',
  `province_id` INT NULL COMMENT '',
  `city_id` INT NULL COMMENT '',
  `region_id` INT NULL COMMENT '',
  `address` VARCHAR(45) NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '')
  ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `taolijie`.`dict_school` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '',
  `full_name` VARCHAR(45) NULL COMMENT '',
  `short_name` VARCHAR(10) NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '')
  ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `taolijie`.`stu_certi` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '',
  `created_time` DATETIME NULL COMMENT '',
  `update_time` DATETIME NULL COMMENT '',
  `member_id` INT NOT NULL COMMENT '',
  `name` VARCHAR(10) NULL COMMENT '认证姓名',
  `college_id` INT NULL COMMENT '认证大学',
  `school_id` INT NULL COMMENT '认证学院',
  `clazz` VARCHAR(20) NULL COMMENT '班级',
  `pic_ids` VARCHAR(100) NULL COMMENT '图片id',
  `status` VARCHAR(3) NULL COMMENT '00: 等待审核 01: 审核通过 02; 审核失败',
  `memo` VARCHAR(200) NULL COMMENT '审核原因说明',
  PRIMARY KEY (`id`)  COMMENT '')
  ENGINE = InnoDB
  COMMENT = '学生认证信息表';

CREATE TABLE IF NOT EXISTS `taolijie`.`emp_certi` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '',
  `company_name` VARCHAR(45) NULL COMMENT '',
  `address` VARCHAR(45) NULL COMMENT '',
  `pic_ids` VARCHAR(100) NULL COMMENT '',
  `status` VARCHAR(3) NULL COMMENT '00: 等待审核 01: 审核通过 02; 审核失败',
  `created_time` DATETIME NULL COMMENT '',
  `update_time` DATETIME NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '')
  ENGINE = InnoDB;