alter table taolijie.member drop column creadits;
alter table taolijie.member add column credits int default 0;
alter table taolijie.member add column user_level varchar(10) default 'LV0';