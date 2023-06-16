CREATE DATABASE users
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

CREATE SCHEMA users_schema
    AUTHORIZATION postgres;

CREATE TABLE users_schema.users
(
    id integer NOT NULL,
    name character varying NOT NULL,
    surname character varying NOT NULL,
    middle_name character varying,
    gender character varying,
    birthday date NOT NULL,
    city_id integer NOT NULL,
    image_url character varying,
    description character varying,
    nickname character varying NOT NULL,
    hardskills character varying,
    email character varying NOT NULL,
    phone character varying NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS users_schema.users
    OWNER to postgres;

CREATE TABLE users_schema.cities
(
    id integer NOT NULL,
    city character varying NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS users_schema.cities
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS users_schema.subscriptions
(
    id integer NOT NULL,
    subscriber_id integer NOT NULL,
    subscription_id integer NOT NULL,
    CONSTRAINT id PRIMARY KEY (id),
    CONSTRAINT subscriptions_subscriber_id_fkey FOREIGN KEY (subscriber_id)
        REFERENCES users_schema.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT subscriptions_subscription_id_fkey FOREIGN KEY (subscription_id)
        REFERENCES users_schema.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS users_schema.subscriptions
    OWNER to postgres;

CREATE INDEX i_users_gender ON users_schema.users (gender);
CREATE INDEX i_users_city ON users_schema.users (city_id);
CREATE INDEX i_users_gender_city ON users_schema.users (gender,city_id);