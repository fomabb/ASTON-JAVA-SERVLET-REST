create table teachers
(
    id     bigserial primary key,
    name   varchar(255) not null,
    status character varying(10)
);

create table courses
(
    id         bigserial primary key,
    name       varchar(255) not null,
    teacher_id integer references teachers (id)
);

create table students
(
    id   bigserial primary key,
    name varchar(255) not null,
    status character varying(10)
);

create table students_courses
(
    student_id integer references students (id),
    courses_id integer references courses (id),
    primary key (student_id, courses_id)
);