CREATE ROLE testuser;
CREATE SCHEMA IF NOT EXISTS test;
grant usage on schema public to postgres;
grant create on schema public to postgres;
revoke ALL on SCHEMA public FROM public;
grant usage on schema test to testuser;
grant SELECT, INSERT, UPDATE, DELETE on ALL tables in schema test TO testuser;
grant SELECT, USAGE on ALL sequences in schema test TO testuser;
ALTER DEFAULT PRIVILEGES FOR ROLE postgres GRANT SELECT,USAGE ON SEQUENCES TO testuser;
ALTER DEFAULT PRIVILEGES FOR ROLE postgres GRANT SELECT,INSERT,DELETE,UPDATE ON TABLES TO testuser;
ALTER TABLE test.employee OWNER TO testuser;

CREATE TABLE test.employee
(
    employee_id BIGSERIAL NOT NULL PRIMARY KEY,
    first_name VARCHAR NOT NULL,
    last_name VARCHAR NOT NULL,
    department_id BIGINT NOT NULL,
    job_title VARCHAR NOT NULL,
    gender VARCHAR NOT NULL,
    date_of_birth DATE NOT NULL
);