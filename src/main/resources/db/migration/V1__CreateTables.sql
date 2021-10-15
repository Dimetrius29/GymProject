CREATE TABLE IF NOT EXISTS client (
    client_id int NOT NULL,
    client_name varchar(100) NOT NULL,
    client_surname varchar(100) NOT NULL,
    client_phone char(13) UNIQUE,
    PRIMARY KEY (client_id)
);

CREATE TABLE IF NOT EXISTS membership (
    client_id int NOT NULL,
    FOREIGN KEY (client_id) REFERENCES client (client_id),
    client_discount int NOT NULL,
    expiration_date date NOT NULL
);

CREATE TABLE IF NOT EXISTS coach (
    coach_id int NOT NULL,
    coach_name varchar(100) NOT NULL,
    coach_surname varchar(100) NOT NULL,
    coach_phone char(13) UNIQUE,
    type_of_activity varchar(100) NOT NULL,
    price_of_activity decimal (4,2) NOT NULL,
    PRIMARY KEY (coach_id)
);

CREATE TABLE IF NOT EXISTS status (
    status_id int NOT NULL,
    status_name varchar(100) NOT NULL,
    PRIMARY KEY (status_id)
);

CREATE TABLE IF NOT EXISTS service_info (
    client_id int NOT NULL,
    coach_id int NOT NULL,
    status_id int NOT NULL,
    FOREIGN KEY (client_id) REFERENCES client (client_id),
    FOREIGN KEY (coach_id) REFERENCES coach (coach_id),
    FOREIGN KEY (status_id) REFERENCES status (status_id),
    date_info date NOT NULL
);