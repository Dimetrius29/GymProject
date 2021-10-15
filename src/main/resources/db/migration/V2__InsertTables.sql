INSERT INTO user(login, password, name, surname, phone)
VALUES ('admin', 'admin', 'Pavel', 'Smirnov', '+375445855685'),
       ('ivan_danilov', '+375299633568', 'Ivan', 'Danilov', '+375299633568'),
       ('Brian', 'Nick', 'Nick', 'Brian', '+375336548977'),
       ('Pinkman', 'Jessy', 'Jessy', 'Pinkman', '+375441483369');

INSERT INTO role
VALUES (1, 'admin'),
       (2, 'client');

INSERT INTO user_role
VALUES (1, 1),
       (2, 2),
       (3, 2),
       (4, 2);

INSERT INTO membership
VALUES (1, 25, '2021-11-21'),
       (4, 10, '2022-01-14');

INSERT INTO coach(name,surname,phone,type_of_activity, price_of_activity)
VALUES ('Pavel', 'Baranov', '+375336543352', 'bodybuilding', 23.50),
       ('Nikita', 'Trybetskoy', '+375449875532', 'cardio', 18.00);

INSERT INTO schedule
VALUES (1, '10:00 - 11:30'),
       (2, '11:30 - 13:00'),
       (3, '13:00 - 14:30'),
       (4, '15:30 - 17:00'),
       (5, '17:00 - 18:30'),
       (6, '18:30 - 20:00');

INSERT INTO service_info
VALUES (1, 1, '2021-11-11',1),
       (1, 1, '2021-11-11',2),
       (2, 2, '2021-11-13',3),
       (2, 2, '2021-11-13',4),
       (3, 1, '2021-11-21',4),
       (3, 1, '2021-11-21',6);

