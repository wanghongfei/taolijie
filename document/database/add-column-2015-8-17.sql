alter table taolijie.member add column app_token varchar(25);
create index idx_member_app_token ON taolijie.member (app_token);