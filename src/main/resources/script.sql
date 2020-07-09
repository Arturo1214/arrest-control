INSERT INTO "jhi_authority" VALUES ('ROLE_RECEPTIONIST');
INSERT INTO "jhi_authority" VALUES ('ROLE_DISPATCHER');

UPDATE databasechangelog set md5sum = null where id = '00000000000001';

-- 30 06 2020
alter table register_case add column support_patrol varchar(255);
alter table register_case add column check_date timestamp;
update databasechangelog set md5sum = null where "id" = '20200530193147-1';
