CREATE TABLE IF NOT EXISTS `taolijie`.`feedback` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '反馈表',
  `phone` VARCHAR(20) NULL COMMENT '',
  `email` VARCHAR(45) NULL COMMENT '',
  `created_time` TIMESTAMP NULL COMMENT '',
  `title` VARCHAR(30) NULL COMMENT '',
  `content` VARCHAR(500) NULL COMMENT '',
  `member_id` INT NULL COMMENT '反馈者的用户id. 如果没有则留空',
  `username` VARCHAR(45) NULL COMMENT '冗余字段，保存member表的用户名',
  PRIMARY KEY (`id`)  )
ENGINE = InnoDB