create table image
(
    id            uuid not null,
    name          varchar(2048),
    extension     varchar(255),
    creation_date timestamp,
    url           varchar(2048),
    primary key (id)
);

alter table event
    ADD logo_id uuid;
alter table usr
    ADD avatar_id uuid;
alter table event_post
    ADD logo_id uuid;

alter table if exists usr
    add constraint usr_image_fk foreign key (avatar_id) references image;
alter table if exists event
    add constraint event_image_fk foreign key (logo_id) references image;
alter table if exists event_post
    add constraint event_post_image_fk foreign key (logo_id) references image;