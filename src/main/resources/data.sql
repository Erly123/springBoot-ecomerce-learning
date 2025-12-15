-- tb_products
INSERT IGNORE INTO tb_products (product_id, product_name, price)
VALUES
    (1, 'Computer', 4500.50),
    (2, 'Smartphone', 2000.00),
    (3, 'Mouse', 200.00);


-- tb_tags
INSERT IGNORE INTO tb_tags (tag_id, name)
VALUES
    (1, 'Eletronics'),
    (2, 'Home'),
    (3, 'Apple');


-- tb_products_tags
INSERT IGNORE INTO tb_products_tags (product_id, tag_id)
VALUES
    (1, 1),
    (2, 3),
    (2, 1),
    (3, 1);
