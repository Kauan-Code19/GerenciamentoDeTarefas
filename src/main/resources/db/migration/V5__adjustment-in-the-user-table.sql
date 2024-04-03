CREATE SEQUENCE user_id_seq;

ALTER TABLE "user"
ALTER COLUMN id SET DEFAULT nextval('user_id_seq');