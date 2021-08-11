create sequence hibernate_sequence start 1 increment 1;

create table comment
(
    id               int8 not null,
    publication_date timestamp,
    text             varchar(255),
    author_id        int8,
    post_id          int8,
    primary key (id)
);
create table event
(
    event_id                int8         not null,
    title                   varchar(255) not null,
    creation_date           timestamp,
    description             varchar(255),
    registration_end_date   timestamp,
    registration_start_date timestamp,
    primary key (event_id)
);
create table event_admins
(
    event_event_id int8 not null,
    admins_id      int8 not null
);
create table event_post
(
    id               int8 not null,
    image_path       varchar(2048),
    publication_date timestamp,
    text             varchar(2048),
    title            varchar(255),
    author_id        int8,
    event_event_id   int8,
    primary key (id)
);
create table registration_request
(
    registration_request_id int8 not null,
    registration_time       timestamp,
    event_id                int8 not null,
    usr_id                  int8 not null,
    primary key (registration_request_id)
);
create table user_roles
(
    user_id int8 not null,
    roles   varchar(255)
);
create table usr
(
    id              int8         not null,
    activation_code varchar(255),
    email           varchar(255),
    email_validated boolean      not null,
    password        varchar(255) not null,
    username        varchar(255) not null,
    primary key (id)
);
alter table if exists comment add constraint comment_author_fk foreign key (author_id) references usr;
alter table if exists comment add constraint comment_post_fk foreign key (post_id) references event_post;
alter table if exists event_admins add constraint event_admins_admin_fk foreign key (admins_id) references usr;
alter table if exists event_admins add constraint event_admins_event_fk foreign key (event_event_id) references event;
alter table if exists event_post add constraint event_post_author_fk foreign key (author_id) references usr;
alter table if exists event_post add constraint event_post_event_fk foreign key (event_event_id) references event;
alter table if exists registration_request add constraint registration_request_event_fk foreign key (event_id) references event;
alter table if exists registration_request add constraint registration_request_user_fk foreign key (usr_id) references usr;
alter table if exists user_roles add constraint user_role_user_fk foreign key (user_id) references usr;