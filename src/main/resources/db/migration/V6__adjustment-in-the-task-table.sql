CREATE SEQUENCE task_id_seq;

ALTER TABLE task
ALTER COLUMN id SET DEFAULT nextval('task_id_seq')