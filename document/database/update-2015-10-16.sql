alter table taolijie.member change column verified id_certi varchar(3) default '00';
alter table taolijie.member add column stu_certi varchar(3) default '00';
alter table taolijie.member add column emp_certi varchar(3) DEFAULT '00';

ALTER TABLE taolijie.id_certi add column status VARCHAR(3);