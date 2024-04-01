AlTER TABLE task
ALTER COLUMN status TYPE smallint
USING CASE
    WHEN status = 'COMPLETE' THEN 0
    WHEN status = 'PROGRESS' THEN 1
    WHEN status = 'PENDING' THEN 2
END;