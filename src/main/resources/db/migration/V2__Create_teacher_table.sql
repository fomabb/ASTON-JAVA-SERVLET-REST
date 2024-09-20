CREATE TABLE teacher
(
    id        BIGSERIAL PRIMARY KEY,
    name      VARCHAR(255),
    status    VARCHAR(255),
    course_id BIGINT,
    FOREIGN KEY (course_id) REFERENCES course (id)
);