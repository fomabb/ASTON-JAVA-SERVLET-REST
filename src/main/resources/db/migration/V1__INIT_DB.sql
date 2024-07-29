create sequence base_entity_sequence;

alter sequence base_entity_sequence owner to postgres;

create table baseentity
(
    id bigint not null
        primary key
);

alter table baseentity
    owner to postgres;

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
    id        bigserial
        primary key,
    name      varchar(255),
    status    varchar(255),
    course_id bigint
        constraint fkf75wvk4ch3gnhje998pq0lcid
            references course
);

alter table teacher
    owner to postgres;

create table student
(
    id         bigserial
        primary key,
    name       varchar(255),
    status     varchar(255),
    teacher_id bigint
        constraint fk3mphcmldvs29jl1w40ssg300j
            references teacher
);

alter table student
    owner to postgres;

create table student_course
(
    student_id bigint not null
        constraint fkq7yw2wg9wlt2cnj480hcdn6dq
            references student,
    course_id  bigint not null
        constraint fkejrkh4gv8iqgmspsanaji90ws
            references course
);

alter table student_course
    owner to postgres;