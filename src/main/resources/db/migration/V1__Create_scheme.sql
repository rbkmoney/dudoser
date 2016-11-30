create schema if not exists dudos;

create table dudos.last_event_id
(
  id int not null,
  event_id bigint,
  constraint event_pkey primary key (id)
);

-- Table: dudos.merchant_shop_template_types

CREATE TABLE dudos.merchant_shop_template_types
(
    id smallint NOT NULL,
    code character varying(100) NOT NULL,
    description character varying(300),
    CONSTRAINT pk_merch_shop_templ_types PRIMARY KEY (id)
);

ALTER TABLE dudos.merchant_shop_template_types
    OWNER to postgres;
COMMENT ON TABLE dudos.merchant_shop_template_types
    IS 'Types of messages (for examle "invoice status changed", "payment started")';

-- Table: dudos.templates

CREATE TABLE dudos.templates
(
    id bigint NOT NULL,
    code character varying(50),
    body text,
    CONSTRAINT pk_templates PRIMARY KEY (id)
);

COMMENT ON TABLE dudos.templates
    IS 'Table with templates for messages';

-- Table: dudos.merchant_shop_bind

CREATE TABLE dudos.merchant_shop_bind
(
    id bigint NOT NULL,
    merch_id character varying(50),
    shop_id character varying(50),
    template_id bigint NOT NULL,
    type smallint NOT NULL,
    CONSTRAINT pk_merch_shop_bind PRIMARY KEY (id),
    CONSTRAINT fk_merch_templates FOREIGN KEY (template_id)
        REFERENCES dudos.templates (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_merch_types FOREIGN KEY (type)
        REFERENCES dudos.merchant_shop_template_types (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

COMMENT ON TABLE dudos.merchant_shop_bind
    IS 'Binding merchant shops with templates';
COMMENT ON CONSTRAINT fk_merch_templates ON dudos.merchant_shop_bind
    IS 'Binding merch_shop.template_id to templates.id';
COMMENT ON CONSTRAINT fk_merch_types ON dudos.merchant_shop_bind
    IS 'Binding with template types';
