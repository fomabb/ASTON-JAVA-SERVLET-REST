create table user_test_index
(
    id       BIGSERIAL    NOT NULL PRIMARY KEY,
    login    VARCHAR(25)  NOT NULL UNIQUE,
    email    VARCHAR(25)  NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

