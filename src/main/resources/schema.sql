set mode MySQL;

DROP TABLE IF EXISTS users;

CREATE TABLE users (
     id varchar(10) primary key,
     name varchar(20) not null,
     password VARCHAR(10) not null
);

-- INSERT INTO testtbl (name) VALUES
-- ('name1'),
-- ('name2'),
-- ('name3');