CREATE TYPE dudos.exclusion_rule_type AS ENUM ('shop');

CREATE TABLE dudos.mailing_exclusion_rules
(
    id    BIGSERIAL                 NOT NULL,
    name  CHARACTER VARYING         NOT NULL,
    type  dudos.exclusion_rule_type NOT NULL,
    value CHARACTER VARYING         NOT NULL,
    CONSTRAINT mailing_exclusion_rules_pkey PRIMARY KEY (id)
);
CREATE INDEX mailing_exclusion_rules_type_idx ON dudos.mailing_exclusion_rules (type);
