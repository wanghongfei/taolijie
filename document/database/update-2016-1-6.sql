CREATE TABLE IF NOT EXISTS `taolijie`.`mem_session` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '',
  `sid` VARCHAR(50) NOT NULL COMMENT '',
  `mem_id` INT NOT NULL COMMENT '',
  `created_time` DATETIME NOT NULL COMMENT '',
  `flush_time` DATETIME NULL COMMENT '最近活动时间',
  PRIMARY KEY (`id`)  COMMENT '',
  INDEX `idx_sid` (`sid` ASC)  COMMENT '',
  INDEX `idx_mem_id` (`mem_id` ASC)  COMMENT '',
  INDEX `idx_flush_time` (`flush_time` ASC)  COMMENT '',
  INDEX `idx_created_time` (`created_time` ASC)  COMMENT '')
ENGINE = InnoDB