CREATE SCHEMA IF NOT EXISTS applications_schema;

SET SCHEMA 'applications_schema';

CREATE TYPE user_type AS ENUM (
    'applicant',
    'advisor'
    );

CREATE TYPE advisor_role AS ENUM (
    'associate',
    'partner',
    'senior'
    );

CREATE TYPE phone_number_type AS ENUM (
    'home',
    'work',
    'mobile'
    );

CREATE TYPE application_status AS ENUM (
    'new',
    'assigned',
    'on_hold',
    'approved',
    'canceled',
    'declined'
    );

CREATE TABLE IF NOT EXISTS users
(
    id        BIGSERIAL,
    username  VARCHAR(255) NOT NULL,
    email     VARCHAR(255) NOT NULL,
    user_type user_type    NOT NULL,
    version   BIGINT       NOT NULL DEFAULT 0,

    CONSTRAINT users_PK PRIMARY KEY (id),
    CONSTRAINT users_username_UK UNIQUE (username),
    CONSTRAINT users_email_UK UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS advisors
(
    user_id BIGINT,
    role    advisor_role NOT NULL,

    CONSTRAINT advisors_PK PRIMARY KEY (user_id),
    CONSTRAINT advisors_users_FK FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS applicants
(
    user_id    BIGINT,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    ssn        VARCHAR(255) NOT NULL,
    version    BIGINT       NOT NULL DEFAULT 0,

    CONSTRAINT applicants_PK PRIMARY KEY (user_id),
    CONSTRAINT applicants_users_FK FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS addresses
(
    applicant_id  BIGINT,
    city          VARCHAR(255) NOT NULL,
    street        VARCHAR(255) NOT NULL,
    street_number VARCHAR(255) NOT NULL,
    zip           VARCHAR(5)   NOT NULL,
    apt           VARCHAR(5),
    version       BIGINT       NOT NULL DEFAULT 0,

    CONSTRAINT addresses_PK PRIMARY KEY (applicant_id),
    CONSTRAINT addresses_applicants_FK FOREIGN KEY (applicant_id) REFERENCES applicants (user_id) ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS phone_numbers
(
    applicant_id BIGINT            NOT NULL,
    phone_number VARCHAR(15)       NOT NULL,
    "type"       phone_number_type NOT NULL,
    version      BIGINT            NOT NULL DEFAULT 0,

    CONSTRAINT phone_numbers_PK PRIMARY KEY (applicant_id, phone_number),
    CONSTRAINT phone_numbers_applicants_FK FOREIGN KEY (applicant_id) REFERENCES applicants (user_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS applications
(
    id           BIGSERIAL,
    money        numeric(21, 4)           NOT NULL,
    "status"     application_status       NOT NULL          DEFAULT 'new',
    created_at   TIMESTAMP WITH TIME ZONE NOT NULL NOT NULL DEFAULT now(),
    assigned_at  TIMESTAMP WITH TIME ZONE,
    applicant_id BIGINT                   NOT NULL,
    advisor_id   BIGINT,
    version      BIGINT                   NOT NULL          DEFAULT 0,

    CONSTRAINT applications_PK PRIMARY KEY (id),
    CONSTRAINT applications_applicants_FK FOREIGN KEY (applicant_id) REFERENCES applicants (user_id) ON DELETE RESTRICT,
    CONSTRAINT applications_advisors_FK FOREIGN KEY (advisor_id) REFERENCES advisors (user_id) ON DELETE RESTRICT,
    CONSTRAINT check_assigned_at CHECK ((assigned_at IS NULL AND advisor_id IS NULL AND "status" = 'new') OR
                                        (advisor_id IS NOT NULL AND "status" != 'new'))
);
