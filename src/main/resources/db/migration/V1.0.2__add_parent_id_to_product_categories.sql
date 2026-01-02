ALTER TABLE
    product_categories ADD parent_id VARCHAR(255);

ALTER TABLE
    product_categories ADD CONSTRAINT fk_product_categories_parent FOREIGN KEY(parent_id) REFERENCES product_categories(id);
