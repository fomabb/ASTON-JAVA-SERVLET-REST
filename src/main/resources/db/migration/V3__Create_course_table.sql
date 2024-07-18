CREATE TABLE IF NOT EXISTS Course
(
    id         SERIAL PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    teacher_id BIGINT,
    FOREIGN KEY (teacher_id) REFERENCES Teacher (id)
);