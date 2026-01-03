CREATE TABLE inventory_inventories (
    product_id VARCHAR(255) PRIMARY KEY,
    quantity INTEGER NOT NULL DEFAULT 0,
    CONSTRAINT fk_inventory_product FOREIGN KEY (product_id) REFERENCES product_products(id)
);
