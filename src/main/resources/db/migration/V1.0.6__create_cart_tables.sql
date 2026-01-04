CREATE
    TABLE
        order_carts(
            id VARCHAR(255) PRIMARY KEY,
            user_id VARCHAR(255) NOT NULL
        );

CREATE
    TABLE
        order_cart_items(
            cart_id VARCHAR(255) NOT NULL,
            product_id VARCHAR(255) NOT NULL,
            quantity INTEGER NOT NULL,
            CONSTRAINT pk_order_cart_items PRIMARY KEY(
                cart_id,
                product_id
            ),
            CONSTRAINT fk_order_cart_items_cart_id FOREIGN KEY(cart_id) REFERENCES order_carts(id) ON
            DELETE
                CASCADE
        );
