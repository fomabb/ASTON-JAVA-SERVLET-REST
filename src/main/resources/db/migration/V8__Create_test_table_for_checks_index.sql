create table user_test_index
(
    id bigserial not null primary key,
    login varchar(25) not null unique,
    email varchar(25) not null unique,
    password varchar(255) not null
);

