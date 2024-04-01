CREATE TABLE task (
    id BIGINT NOT NULL UNIQUE,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    due_data DATE NOT NULL,
    status VARCHAR(255) NOT NULL
)