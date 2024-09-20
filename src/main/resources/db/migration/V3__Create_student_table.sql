CREATE TABLE student
(
    id         BIGSERIAL NOT NULL PRIMARY KEY,
    name       VARCHAR(255),
    status     VARCHAR(255),
    teacher_id BIGINT,
    FOREIGN KEY (teacher_id) REFERENCES teacher (id)
);