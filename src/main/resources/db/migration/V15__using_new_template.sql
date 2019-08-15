CREATE SEQUENCE merch_shop_bind_seq;
SELECT setval('merch_shop_bind_seq', (SELECT MAX(id) FROM dudos.merchant_shop_bind));

insert into dudos.merchant_shop_bind(id, type, merch_id, shop_id, template_id, is_active)
    values (nextval('merch_shop_bind_seq'), 2, 'a1bc0bf1-5659-4e84-aad1-36d6ae7b9deb', '7900b13f-b79a-47a3-8eb9-357a24429efe', 11, true);

insert into dudos.merchant_shop_bind(id, type, merch_id, shop_id, template_id, is_active)
    values (nextval('merch_shop_bind_seq'), 2, 'a1bc0bf1-5659-4e84-aad1-36d6ae7b9deb', '7f48d7bd-d48f-44a3-badb-6bba0939b554 ', 11, true);
