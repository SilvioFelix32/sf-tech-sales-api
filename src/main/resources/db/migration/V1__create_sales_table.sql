CREATE TABLE IF NOT EXISTS sales (
    sale_id VARCHAR(255) PRIMARY KEY,
    company_id VARCHAR(255) NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    total DOUBLE PRECISION NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);
CREATE TABLE IF NOT EXISTS sale_items (
    sale_item_id VARCHAR(255) PRIMARY KEY,
    sale_id VARCHAR(255) NOT NULL,
    category_id VARCHAR(255) NOT NULL,
    product_id VARCHAR(255) NOT NULL,
    url_banner VARCHAR(255),
    total_value DOUBLE PRECISION NOT NULL,
    sku VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    subtitle VARCHAR(255),
    description TEXT,
    quantity INTEGER NOT NULL,
    FOREIGN KEY (sale_id) REFERENCES sales(sale_id)
);