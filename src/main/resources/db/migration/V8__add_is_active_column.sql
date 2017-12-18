ALTER TABLE dudos.merchant_shop_bind ADD COLUMN is_active boolean DEFAULT true;

UPDATE dudos.merchant_shop_bind SET is_active = true;

INSERT INTO dudos.merchant_shop_bind(id, merch_id, shop_id, type, template_id, is_active) VALUES (4, 'd6f1c81f-7600-4aae-aa59-5a79fe634a3d', '3ad07fb1-56e4-47c3-8c7d-a9521b7ce70e', 2, 3, false);
INSERT INTO dudos.merchant_shop_bind(id, merch_id, shop_id, type, template_id, is_active) VALUES (5, 'd6f1c81f-7600-4aae-aa59-5a79fe634a3d', '3ad07fb1-56e4-47c3-8c7d-a9521b7ce70e', 3, 4, false);