create schema if not exists dudos;

create table dudos.last_event_id
(
  id int not null,
  event_id bigint,
  constraint event_pkey primary key (id)
);

insert into dudos.last_event_id (id) values (1);
