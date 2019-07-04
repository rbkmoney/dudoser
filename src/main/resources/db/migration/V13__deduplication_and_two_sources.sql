CREATE TABLE dudos.mailing_list
(
  subject character varying(100) primary key,
  receiver character varying(100),
  body text,
  date_created timestamp,
  sent boolean
);