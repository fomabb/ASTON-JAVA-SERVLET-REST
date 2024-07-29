create table base_entity
(
    id bigint not null
        primary key
);

alter table base_entity
    owner to postgres;

create table course
(
    id   bigint not null
        primary key
        constraint fkeu1ggpyuwaa3yx52ssaa39rpg
            references base_entity,
    name varchar(255)
);

alter table course
    owner to postgres;

create table teacher
(
    course_id bigint
        constraint fkf75wvk4ch3gnhje998pq0lcid
            references course,
    id        bigint not null
        primary key
        constraint fk8aulg3lkh51bt84dmjdfop69a
            references base_entity,
    name      varchar(255),
    status    varchar(255)
        constraint teacher_status_check
            check ((status)::text = ANY ((ARRAY ['STUDENT'::character varying, 'TEACHER'::character varying])::text[]))
);

alter table teacher
    owner to postgres;

create table student
(
    id         bigint not null
        primary key
        constraint fkb1vxrdtbnavpod4bd0yapr9of
            references base_entity,
    teacher_id bigint
        constraint fk3mphcmldvs29jl1w40ssg300j
            references teacher,
    name       varchar(255),
    status     varchar(255)
        constraint student_status_check
            check ((status)::text = ANY ((ARRAY ['STUDENT'::character varying, 'TEACHER'::character varying])::text[]))
);

alter table student
    owner to postgres;

create table student_course
(
    course_id  bigint not null
        constraint fkejrkh4gv8iqgmspsanaji90ws
            references course,
    student_id bigint not null
        constraint fkq7yw2wg9wlt2cnj480hcdn6dq
            references student
);

alter table student_course
    owner to postgres;