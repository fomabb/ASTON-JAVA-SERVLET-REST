CREATE TABLE IF NOT EXISTS Teacher
(
    id     SERIAL PRIMARY KEY,
    name   VARCHAR(255) NOT NULL,
    status VARCHAR(50)  NOT NULL
);

CREATE TABLE IF NOT EXISTS Student
(
    id         SERIAL PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    status     VARCHAR(50)  NOT NULL,
    teacher_id BIGINT,
    FOREIGN KEY (teacher_id) REFERENCES Teacher (id)
);

CREATE TABLE IF NOT EXISTS Course
(
    id         SERIAL PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    teacher_id BIGINT,
    FOREIGN KEY (teacher_id) REFERENCES Teacher (id)
);

CREATE TABLE IF NOT EXISTS students_courses
(
    student_id BIGINT NOT NULL,
    course_id  BIGINT NOT NULL,
    PRIMARY KEY (student_id, course_id),
    FOREIGN KEY (student_id) REFERENCES Student (id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES Course (id) ON DELETE CASCADE
);