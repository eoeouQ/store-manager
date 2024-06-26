CREATE TABLE IF NOT EXISTS stores
(
    id       BIGSERIAL PRIMARY KEY,
    location VARCHAR(64) NOT NULL
);

CREATE TABLE IF NOT EXISTS products
(
    id    BIGSERIAL PRIMARY KEY,
    label VARCHAR(32) UNIQUE NOT NULL,
    price NUMERIC            NOT NULL CHECK ( price > 0 )
);

CREATE TABLE IF NOT EXISTS stored_products
(
    id         BIGSERIAL PRIMARY KEY,
    product_id BIGINT REFERENCES products (id),
    store_id   BIGINT REFERENCES stores (id),
    stored     INTEGER CHECK ( stored >= 0 )
);

CREATE TABLE IF NOT EXISTS orders
(
    id                 BIGSERIAL PRIMARY KEY,
    stored_product_id BIGINT REFERENCES stored_products (id),
    status             VARCHAR(32) NOT NULL,
    quantity           INTEGER CHECK ( quantity > 0),
    placed_at          TIMESTAMP   NOT NULL DEFAULT NOW()
);