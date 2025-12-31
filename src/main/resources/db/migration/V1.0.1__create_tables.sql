CREATE TABLE product_categories (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE product_products (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price_amount NUMERIC(19, 0) NOT NULL,
    category_id VARCHAR(255) NOT NULL,
    CONSTRAINT fk_product_category FOREIGN KEY (category_id) REFERENCES product_categories(id)
);
