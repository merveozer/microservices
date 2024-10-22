CREATE TABLE "orders"
(
    id BIGINT NOT NULL DEFAULT nextval('orders_id_seq'::regclass),
    order_number VARCHAR(255),
    price NUMERIC(38,2),
    quantity INTEGER,
    sku_code VARCHAR(255),
    CONSTRAINT orders_pkey PRIMARY KEY (id)
);
