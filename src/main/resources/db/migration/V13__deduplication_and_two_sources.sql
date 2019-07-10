CREATE TABLE dudos.mailing_list
(
  subject character varying(100) primary key,
  receiver character varying(100),
  body text,
  date_created timestamp,
  sent boolean
);

CREATE UNIQUE INDEX invoice_payment_refund_unique ON dudos.payment_payer (invoice_id, payment_id, refund_id)
  WHERE payment_id IS NOT NULL AND refund_id IS NOT NULL;

CREATE UNIQUE INDEX invoice_payment_unique ON dudos.payment_payer (invoice_id, payment_id)
  WHERE payment_id IS NOT NULL AND refund_id IS NULL;

CREATE UNIQUE INDEX invoice_unique ON dudos.payment_payer (invoice_id)
  WHERE payment_id IS NULL AND refund_id IS NULL;