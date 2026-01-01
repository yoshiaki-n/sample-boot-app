-- product_categories
INSERT
    INTO
        product_categories(
            id,
            name
        )
    VALUES(
        'C001',
        'Electronics'
    );

INSERT
    INTO
        product_categories(
            id,
            name
        )
    VALUES(
        'C002',
        'Books'
    );

-- product_products
INSERT
    INTO
        product_products(
            id,
            name,
            description,
            price_amount,
            category_id
        )
    VALUES(
        'P001',
        'Smartphone',
        'Latest model',
        100000,
        'C001'
    );

INSERT
    INTO
        product_products(
            id,
            name,
            description,
            price_amount,
            category_id
        )
    VALUES(
        'P002',
        'Laptop',
        'High performance',
        200000,
        'C001'
    );

INSERT
    INTO
        product_products(
            id,
            name,
            description,
            price_amount,
            category_id
        )
    VALUES(
        'P003',
        'Tech Book',
        'Learn Java',
        3000,
        'C002'
    );
