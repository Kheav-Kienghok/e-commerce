CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    description TEXT,
    price NUMERIC(10,2) NOT NULL,
    stock_qty INT NOT NULL,
    image_url TEXT,
    created_at TIMESTAMP DEFAULT NOW(),
    created_by BIGINT REFERENCES users(id)
);
