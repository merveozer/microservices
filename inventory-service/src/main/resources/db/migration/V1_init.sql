CREATE TABLE IF NOT EXISTS inventory
(
    id bigint NOT NULL DEFAULT nextval('inventory_id_seq'::regclass),
    quantity integer,
    sku_code character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT inventory_pkey PRIMARY KEY (id)
)