CREATE SEQUENCE IF NOT EXISTS store_sequence AS BIGINT;
CREATE TABLE IF NOT EXISTS store
(
    id       BIGINT PRIMARY KEY DEFAULT nextval('store_sequence'),
    name     VARCHAR(64) NOT NULL,
    location VARCHAR(64) NOT NULL
);

CREATE SEQUENCE IF NOT EXISTS product_sequence AS BIGINT;
CREATE TABLE IF NOT EXISTS product
(
    id    BIGINT PRIMARY KEY DEFAULT nextval('product_sequence'),
    label VARCHAR(64) UNIQUE NOT NULL,
    price INT                NOT NULL CHECK ( price > 0 )
);

CREATE TABLE IF NOT EXISTS stored_product
(
    product_id BIGINT REFERENCES product (id),
    store_id   BIGINT REFERENCES store (id),
    quantity   INT CHECK ( quantity >= 0 ),
    PRIMARY KEY (product_id, store_id)
);

CREATE TYPE ORDER_STATUS
AS ENUM ('STATUS_CREATED', 'STATUS_DELIVERING', 'STATUS_DELIVERED');

CREATE SEQUENCE IF NOT EXISTS order_sequence AS BIGINT;
CREATE TABLE IF NOT EXISTS "order"
(
    id          BIGINT PRIMARY KEY    DEFAULT nextval('order_sequence'),
    user_id     BIGINT       NOT NULL,
    total_price INT          NOT NULL,
    status      ORDER_STATUS NOT NULL,
    date        TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS order_position
(
    order_id   BIGINT REFERENCES "order" (id),
    product_id BIGINT REFERENCES product (id),
    store_id   BIGINT REFERENCES store (id),
    quantity   INT CHECK ( quantity > 0)
);