CREATE TABLE order_items (
    id BIGSERIAL PRIMARY KEY,
    quantity INT NOT NULL,
    price_at_buy NUMERIC(10,2) NOT NULL,
    order_id BIGINT REFERENCES orders(id),
    product_id BIGINT REFERENCES products(id)
);
