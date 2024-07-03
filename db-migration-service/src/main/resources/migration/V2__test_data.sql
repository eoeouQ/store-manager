INSERT INTO product (label, price)
VALUES ('Mobile phone', 10);
INSERT INTO product (label, price)
VALUES ('Burger', 3);
INSERT INTO product (label, price)
VALUES ('Hand washer', 4);

INSERT INTO store (name, location)
VALUES ('Minsk S5', 'LOCATION_BELARUS');
INSERT INTO store (name, location)
VALUES ('Gomel S3', 'LOCATION_BELARUS');
INSERT INTO store (name, location)
VALUES ('Beijing S617', 'LOCATION_CHINA');

INSERT INTO stored_product (product_id, store_id, quantity)
VALUES (1, 1, 100);
INSERT INTO stored_product (product_id, store_id, quantity)
VALUES (2, 1, 33);
INSERT INTO stored_product (product_id, store_id, quantity)
VALUES (1, 3, 17);
INSERT INTO stored_product (product_id, store_id, quantity)
VALUES (3, 2, 121);

INSERT INTO order_position (stored_product_id, order_id, quantity)
VALUES (1, 1, 10);
INSERT INTO order_position (stored_product_id, order_id, quantity)
VALUES (2, 1, 15);
INSERT INTO order_position (stored_product_id, order_id, quantity)
VALUES (1, 2, 20);
INSERT INTO order_position (stored_product_id, order_id, quantity)
VALUES (3, 2, 10);
INSERT INTO order_position (stored_product_id, order_id, quantity)
VALUES (4, 2, 15);
INSERT INTO order_position (stored_product_id, order_id, quantity)
VALUES (2, 3, 25);
INSERT INTO order_position (stored_product_id, order_id, quantity)
VALUES (4, 3, 50);

INSERT INTO order (user_id, total_price, status, date)
VALUES (1, 120, 'STATUS_CREATED', NOW());
INSERT INTO order (user_id, total_price, status, dat)
VALUES (2, 270, 'STATUS_DELIVERED', NOW());
INSERT INTO order (user_id, total_price, status, dat)
VALUES (3, 1090, 'STATUS_CREATED', NOW());