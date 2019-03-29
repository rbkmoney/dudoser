update dudos.last_event_id set id = 0;
insert into dudos.last_event_id(id, event_id) values (1, (select event_id from dudos.last_event_id where id = 0));
insert into dudos.last_event_id(id, event_id) values (2, (select event_id from dudos.last_event_id where id = 0));
insert into dudos.last_event_id(id, event_id) values (3, (select event_id from dudos.last_event_id where id = 0));
insert into dudos.last_event_id(id, event_id) values (4, (select event_id from dudos.last_event_id where id = 0));