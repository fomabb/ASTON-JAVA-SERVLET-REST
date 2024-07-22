create table course
(
    id   bigserial
        primary key,
    name varchar(255)
);

alter table course
    owner to postgres;

create table teacher
(
    course_id bigint
        constraint fk_course_id
            references course,
    id        bigserial
        primary key,
    name      varchar(255),
    status    varchar(255)
        constraint teacher_status_check
            check ((status)::text = ANY ((ARRAY ['STUDENT'::character varying, 'TEACHER'::character varying])::text[]))
);

alter table teacher
    owner to postgres;

create table student
(
    id         bigserial
        primary key,
    teacher_id bigint
        constraint fk_teacher_id
            references teacher,
    name       varchar(255),
    status     varchar(255)
        constraint student_status_check
            check ((status)::text = ANY ((ARRAY ['STUDENT'::character varying, 'TEACHER'::character varying])::text[]))
);

alter table student
    owner to postgres;

create table students_courses
(
    course_id  bigint
        constraint fk_course_id
            references course,
    id         bigserial
        primary key,
    student_id bigint
        constraint fk_student_id
            references student
);

alter table students_courses
    owner to postgres;