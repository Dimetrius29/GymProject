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

INSERT INTO coach(name,surname,phone,specialization, price_of_activity)
VALUES ('Pavel', 'Baranov', '+375336543352', 'bodybuilding', 23.50),
       ('Nikita', 'Trybetskoy', '+375449875532', 'cardio', 18.00);

INSERT INTO service_info
VALUES (1, 1, '2021-11-11', '14:00', '15:30'),
       (1, 1, '2021-11-11','13:00', '14:30'),
       (2, 2, '2021-11-13','14:00', '15:30'),
       (2, 2, '2021-11-13','16:00', '17:30'),
       (3, 1, '2021-11-21','11:00', '12:30'),
       (3, 1, '2021-11-21','18:00', '19:30');

