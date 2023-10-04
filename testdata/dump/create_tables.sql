-- Схема
create schema sa;

-- Создание таблицы покупателей
create table sa.customers (
	customer_id serial primary key,
	first_name varchar(255),
	last_name varchar(255)
);

-- Создание таблицы товаров
create table sa.pruducts (
	product_id serial primary key,
	product_name varchar(255),
	price decimal(10, 2)
);

-- Создание таблицы покупок
create table sa.purchases (
	purchase_id serial primary key,
	customer_id int references sa.customers(customer_id),
	product_id int references sa.products(product_id),
	purchase_date date
);

-- Рандомайзер для заполнения таблицы purchases но основе содержания pruducts и customers
INSERT INTO sa.purchases (customer_id, product_id, purchase_date)
SELECT
    (SELECT customer_id FROM sa.customers ORDER BY RANDOM() LIMIT 1), -- Выбор случайного покупателя
    (SELECT product_id FROM sa.products ORDER BY RANDOM() LIMIT 1), -- Выбор случайного товара
    CURRENT_DATE - (FLOOR(RANDOM() * 1000) || ' days')::INTERVAL; -- Выбор случайной даты в пределах 1000 назад