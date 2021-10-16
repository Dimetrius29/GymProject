CREATE TABLE IF NOT EXISTS user (
    id int IDENTITY NOT NULL PRIMARY KEY,
    login varchar(50) NOT NULL UNIQUE,
    password varchar(50) NOT NULL,
    name varchar(50) NOT NULL,
    surname varchar(50) NOT NULL,
    phone char(13) UNIQUE
);

CREATE TABLE IF NOT EXISTS role
(
    id int NOT NULL,
    name char(20) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS user_role
(
    user_id int NOT NULL,
    role_id int NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (role_id) REFERENCES role (id)
);

CREATE TABLE IF NOT EXISTS membership (
    user_id int NOT NULL,
    discount int NOT NULL,
    expiration_date date NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE TABLE IF NOT EXISTS coach (
    id int IDENTITY NOT NULL PRIMARY KEY,
    name varchar(50) NOT NULL,
    surname varchar(50) NOT NULL,
    phone char(13) UNIQUE,
    specialization varchar(50) NOT NULL,
    price_of_activity decimal (4,2) NOT NULL
);

CREATE TABLE IF NOT EXISTS schedule (
    id int NOT NULL,
    time_interval varchar(13) NOT NULL,
    PRIMARY KEY (time_interval)
);

CREATE TABLE IF NOT EXISTS service_info (
    user_id int,
    coach_id int NOT NULL,
    date_info date NOT NULL,
    time_interval varchar(13) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (coach_id) REFERENCES coach (id),
    FOREIGN KEY (time_interval) REFERENCES schedule (id)
);