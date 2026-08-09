SELECT * FROM productdb.product;

INSERT INTO category (id, name) VALUES
(1, 'Electronics'),
(2, 'Clothing');


INSERT INTO sub_category (id, name, category_id) VALUES
(1, 'Mobiles', 1),
(2, 'Laptops', 1),
(3, 'Shirts', 2),
(4, 'Shoes', 2);

INSERT INTO products (id, name, sub_category_id) VALUES
(1, 'iPhone 15', 1),
(2, 'Samsung Galaxy S24', 1),
(3, 'MacBook Pro', 2),
(4, 'Dell XPS', 2),
(5, 'Formal Shirt', 3),
(6, 'Running Shoes', 4);


INSERT INTO brand (id, name, price, product_id)
VALUES
(1, 'Nike', 1999.99, 1),
(2, 'Adidas', 2499.50, 1),
(3, 'Puma', 3499.00, 2);

INSERT INTO image (id, url, brand_id)
VALUES
(1, 'https://example.com/nike1.jpg', 1),
(2, 'https://example.com/nike2.jpg', 1),
(3, 'https://example.com/adidas1.jpg', 2),
(4, 'https://example.com/puma1.jpg', 3);

INSERT INTO size (id, size, quantity, brand_id)
VALUES
(1, 'S', '10', 1),
(2, 'M', '20', 1),
(3, 'L', '15', 1),

(4, 'M', '25', 2),
(5, 'XL', '12', 2),

(6, '40', '8', 3),
(7, '41', '14', 3),
(8, '42', '6', 3);