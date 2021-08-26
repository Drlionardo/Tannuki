create table image
(
    id           uuid not null,
    name         varchar(2048),
    extension    varchar(255),
    creation_date timestamp,
    url         varchar(2048),
    primary key (id)
);
