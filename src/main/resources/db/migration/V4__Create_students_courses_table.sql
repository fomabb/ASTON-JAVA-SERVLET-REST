CREATE TABLE IF NOT EXISTS StudentsCourses
(
    student_id BIGINT NOT NULL,
    course_id  BIGINT NOT NULL,
    PRIMARY KEY (student_id, course_id),
    FOREIGN KEY (student_id) REFERENCES Student (id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES Course (id) ON DELETE CASCADE
);