CREATE TABLE IF NOT EXISTS store
(
    id       BIGSERIAL PRIMARY KEY,
    name     VARCHAR(64) NOT NULL,
    location VARCHAR(64) NOT NULL
);

CREATE TABLE IF NOT EXISTS product
(
    id    BIGSERIAL PRIMARY KEY,
    label VARCHAR(64) UNIQUE NOT NULL,
    price INT                NOT NULL CHECK ( price > 0 )
);

CREATE TABLE IF NOT EXISTS stored_product
(
    id         BIGSERIAL PRIMARY KEY,
    product_id BIGINT REFERENCES product (id),
    store_id   BIGINT REFERENCES store (id),
    quantity   INT CHECK ( quantity >= 0 )
);

CREATE TABLE IF NOT EXISTS order_position
(
    stored_product_id BIGINT REFERENCES stored_product (id),
    order_id          BIGINT REFERENCES order (id),
    quantity          INT CHECK ( quantity > 0),
    PRIMARY KEY (stored_product_id, order_id)
);

CREATE TABLE IF NOT EXISTS order
(
    id          BIGSERIAL PRIMARY KEY,
    user_id     BIGINT      NOT NULL,
    total_price INT         NOT NULL,
    status      VARCHAR(32) NOT NULL,
    date        TIMESTAMP   NOT NULL DEFAULT NOW()
);