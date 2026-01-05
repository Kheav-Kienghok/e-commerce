-- Create enum type for order status
CREATE TYPE order_status_enum AS ENUM ('PENDING', 'SHIPPED', 'PAID', 'CANCELLED');

-- Create orders table
CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,
    status order_status_enum NOT NULL DEFAULT 'PENDING',
    total_price NUMERIC(10,2) NOT NULL,
    created_at TIMESTAMP DEFAULT NOW(),
    user_id BIGINT REFERENCES users(id)
);
