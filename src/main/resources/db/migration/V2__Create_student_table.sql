CREATE TABLE IF NOT EXISTS Student
(
    id         SERIAL PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    status     VARCHAR(50)  NOT NULL,
    teacher_id BIGINT,
    FOREIGN KEY (teacher_id) REFERENCES Teacher (id)
);