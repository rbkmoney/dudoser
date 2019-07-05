CREATE TABLE dudos.mailing_list
(
  subject character varying(100) primary key,
  receiver character varying(100),
  body text,
  date_created timestamp,
  sent boolean
);

ALTER TABLE dudos.payment_payer
  ADD CONSTRAINT invoice_payment_refund_unique
  UNIQUE (invoice_id, payment_id, refund_id);