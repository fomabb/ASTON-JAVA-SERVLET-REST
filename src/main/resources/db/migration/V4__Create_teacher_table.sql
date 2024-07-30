CREATE TABLE teacher
(
    id        BIGINT NOT NULL PRIMARY KEY,
    name      VARCHAR(255),
    status    VARCHAR(255),
    course_id BIGINT,
    FOREIGN KEY (id) REFERENCES base_entity (id),
    FOREIGN KEY (course_id) REFERENCES course (id)
);