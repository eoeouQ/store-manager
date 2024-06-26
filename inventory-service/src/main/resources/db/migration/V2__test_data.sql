INSERT INTO products (label, price)
VALUES ('Телефон', 10.50);
INSERT INTO products (label, price)
VALUES ('Бургер', 3.20);
INSERT INTO products (label, price)
VALUES ('Мазилка', 4.80);

INSERT INTO stores (location)
VALUES ('LOCATION_BELARUS');
INSERT INTO stores (location)
VALUES ('LOCATION_BELARUS');
INSERT INTO stores (location)
VALUES ('LOCATION_CHINA');

INSERT INTO stored_products (product_id, store_id, stored)
VALUES (1, 1, 100);
INSERT INTO stored_products (product_id, store_id, stored)
VALUES (2, 1, 33);
INSERT INTO stored_products (product_id, store_id, stored)
VALUES (1, 3, 17);
INSERT INTO stored_products (product_id, store_id, stored)
VALUES (3, 2, 121);

INSERT INTO orders (stored_product_id, status, quantity) VALUES (1, 'STATUS_CREATED', 13);
INSERT INTO orders (stored_product_id, status, quantity) VALUES (2, 'STATUS_DELIVERED', 6);
INSERT INTO orders (stored_product_id, status, quantity) VALUES (3, 'STATUS_CREATED', 22);