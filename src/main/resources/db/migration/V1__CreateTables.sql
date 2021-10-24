CREATE TABLE IF NOT EXISTS role
(
    id int IDENTITY NOT NULL,
    name char(20) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS user (
    id int IDENTITY NOT NULL PRIMARY KEY,
    login varchar(50) NOT NULL UNIQUE,
    password varchar(255) NOT NULL,
    name varchar(50) NOT NULL,
    surname varchar(50) NOT NULL,
    phone char(13) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS user_role
(
    user_id int NOT NULL,
    role_id int NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (role_id) REFERENCES role (id),
    PRIMARY KEY (user_id,role_id)
);

CREATE TABLE IF NOT EXISTS coach (
    id int IDENTITY NOT NULL PRIMARY KEY,
    name varchar(50) NOT NULL,
    surname varchar(50) NOT NULL,
    phone char(13) UNIQUE,
    specialization varchar(50) NOT NULL,
    price_of_activity decimal (4,2) NOT NULL
);

CREATE TABLE IF NOT EXISTS training (
    user_id int,
    coach_id int NOT NULL,
    date_info date NOT NULL,
    start_time time NOT NULL,
    end_time time NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (coach_id) REFERENCES coach (id)
);