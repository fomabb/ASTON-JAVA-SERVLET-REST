CREATE TABLE course
(
    id   BIGINT NOT NULL PRIMARY KEY,
    name VARCHAR(255),
    FOREIGN KEY (id) REFERENCES base_entity (id)
);