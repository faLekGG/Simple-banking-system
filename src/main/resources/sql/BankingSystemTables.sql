CREATE SCHEMA banking_system;

CREATE TABLE account(
                        id serial PRIMARY KEY,
                        username VARCHAR (50) UNIQUE NOT NULL,
                        password VARCHAR (50) NOT NULL,
                        email VARCHAR (355) UNIQUE NOT NULL,
                        created_on TIMESTAMP NOT NULL,
                        last_login TIMESTAMP,
                        enable boolean DEFAULT false
);

CREATE TABLE registration_token(
                                   id serial PRIMARY KEY,
                                   username VARCHAR (250) UNIQUE NOT NULL,
                                   expiration_date TIMESTAMP NOT NULL,
                                   CONSTRAINT account_id_fkey FOREIGN KEY (account_id)
                                       REFERENCES banking_system.account (id) MATCH SIMPLE
                                       ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE credit_card(
                            id serial PRIMARY KEY,
                            number VARCHAR (50) UNIQUE NOT NULL,
                            pin VARCHAR (50) NOT NULL,
                            balance float(4) DEFAULT 0,
                            account_id integer NOT NULL,
                            CONSTRAINT account_id_fkey FOREIGN KEY (account_id)
                                REFERENCES banking_system.account (id) MATCH SIMPLE
                                ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE role(
                     id serial PRIMARY KEY,
                     name VARCHAR (50) UNIQUE NOT NULL
);

CREATE TABLE account_role(
                             account_id int NOT NULL ,
                             role_id int,
                             CONSTRAINT account_role_id_fkey FOREIGN KEY (account_id)
                                 REFERENCES banking_system.account (id) MATCH FULL
                                 ON UPDATE NO ACTION ON DELETE NO ACTION,
                             CONSTRAINT role_account_id_fkey FOREIGN KEY (role_id)
                                 REFERENCES banking_system.role (id) MATCH SIMPLE
                                 ON UPDATE NO ACTION ON DELETE NO ACTION
);

INSERT INTO role(id, name)
VALUES (1, 'ROLE_USER'), (2, 'ROLE_ADMIN');