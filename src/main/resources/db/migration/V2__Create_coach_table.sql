create table COACH (
    CoachID int not null,
    CoachName varchar(100) not null,
    CoachSurname varchar(100) not null,
    CoachPhone char(13),
    TypeOfActivity varchar(100) not null,
    PRICE DECIMAL not null
);