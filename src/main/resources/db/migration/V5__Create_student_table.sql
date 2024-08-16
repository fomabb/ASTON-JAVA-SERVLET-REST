CREATE TABLE student
(
    id         BIGINT NOT NULL PRIMARY KEY,
    name       VARCHAR(255),
    status     VARCHAR(255),
    teacher_id BIGINT,
    FOREIGN KEY (id) REFERENCES base_entity (id),
    FOREIGN KEY (teacher_id) REFERENCES teacher (id)
);