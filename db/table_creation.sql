CREATE TABLE employee
(
    employee_id BIGSERIAL NOT NULL PRIMARY KEY,
    first_name VARCHAR NOT NULL,
    last_name VARCHAR NOT NULL,
    department_id BIGINT NOT NULL,
    job_title VARCHAR NOT NULL,
    gender VARCHAR NOT NULL,
    date_of_birth TIMESTAMP NOT NULL
);