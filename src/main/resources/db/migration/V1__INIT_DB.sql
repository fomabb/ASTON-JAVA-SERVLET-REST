create table postgres.base_entity
(
    id bigint primary key not null
);

create table postgres.course
(
    id   bigint primary key not null,
    name character varying(255),
    foreign key (id) references postgres.base_entity (id)
        match simple on update no action on delete no action
);

create table postgres.student
(
    id         bigint primary key not null,
    teacher_id bigint,
    name       character varying(255),
    status     character varying(255),
    foreign key (teacher_id) references postgres.teacher (id)
        match simple on update no action on delete no action,
    foreign key (id) references postgres.base_entity (id)
        match simple on update no action on delete no action
);

create table postgres.student_course
(
    course_id  bigint not null,
    student_id bigint not null,
    foreign key (course_id) references postgres.course (id)
        match simple on update no action on delete no action,
    foreign key (student_id) references postgres.student (id)
        match simple on update no action on delete no action
);

create table postgres.teacher
(
    course_id bigint,
    id        bigint primary key not null,
    name      character varying(255),
    status    character varying(255),
    foreign key (id) references postgres.base_entity (id)
        match simple on update no action on delete no action,
    foreign key (course_id) references postgres.course (id)
        match simple on update no action on delete no action
);

