-- Создание таблицы course
CREATE TABLE course
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Создание таблицы teacher
CREATE TABLE teacher
(
    id        SERIAL PRIMARY KEY,
    name      VARCHAR(255) NOT NULL,
    status    character varying(10)  NOT NULL,
    course_id BIGINT,
    CONSTRAINT fk_course
        FOREIGN KEY (course_id)
            REFERENCES course (id)
);

-- Создание таблицы student
CREATE TABLE student
(
    id         SERIAL PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    status     character varying(10)  NOT NULL,
    teacher_id BIGINT,
    CONSTRAINT fk_teacher
        FOREIGN KEY (teacher_id)
            REFERENCES teacher (id)
);

-- Создание таблицы для связи many-to-many между student и course
CREATE TABLE students_courses
(
    student_id BIGINT NOT NULL,
    course_id  BIGINT NOT NULL,
    PRIMARY KEY (student_id, course_id),
    CONSTRAINT fk_student
        FOREIGN KEY (student_id)
            REFERENCES student (id),
    CONSTRAINT fk_course
        FOREIGN KEY (course_id)
            REFERENCES course (id)
);