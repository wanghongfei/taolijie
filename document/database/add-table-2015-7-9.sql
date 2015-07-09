drop table taolijie.banner_pic;

CREATE TABLE IF NOT EXISTS `taolijie`.`banner_pic` (
      `id` INT NOT NULL AUTO_INCREMENT COMMENT '',
      `created_time` DATETIME NULL COMMENT '',
      `picture_id` INT NULL COMMENT 'image_resource表的主键',
      `url` VARCHAR(256) NULL COMMENT '',
      `memo` VARCHAR(256) NULL COMMENT '',
      `order_index` INT NULL COMMENT '排序,值越小越靠前',
      PRIMARY KEY (`id`)  )
    ENGINE = InnoDB;
