alter TABLE taolijie.quest drop column province_id;
alter TABLE taolijie.quest drop column city_id;
alter TABLE taolijie.quest drop column region_id;
alter TABLE taolijie.quest drop column college_id;
alter TABLE taolijie.quest drop column school_id;

alter table taolijie.quest add column is_target_all bool default false;