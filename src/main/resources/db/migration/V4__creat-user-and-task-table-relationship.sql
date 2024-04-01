ALTER TABLE task
ADD COLUMN user_id BIGINT,
ADD CONSTRAINT fk_user_task FOREIGN KEY (user_id) REFERENCES "user"(id);