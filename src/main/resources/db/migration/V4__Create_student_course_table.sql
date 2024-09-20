CREATE TABLE student_course
(
    student_id BIGINT NOT NULL,
    course_id  BIGINT NOT NULL,
    PRIMARY KEY (student_id, course_id),
    FOREIGN KEY (student_id) REFERENCES student (id),
    FOREIGN KEY (course_id) REFERENCES course (id)
);