DROP TABLE IF EXISTS LOGINS;

CREATE TABLE LOGINS (
    id BIGINT AUTO_INCREMENT  PRIMARY KEY,
    username VARCHAR(250) UNIQUE NOT NULL,
    password VARCHAR(250) NOT NULL
);