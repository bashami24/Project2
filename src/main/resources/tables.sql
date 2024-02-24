DROP TABLE BOOK IF EXISTS;

DROP TABLE Seller IF EXISTS;

CREATE TABLE Seller (
    Seller_id int primary key,
    Seller_Name varchar(255) not null unique
);

CREATE TABLE BOOK (
    Product_id int primary key,
    name varchar(255) not null,
    Product_Price decimal (10,2) not null,
    Seller_id int references Seller(Seller_id)
);

INSERT INTO Seller (seller_id,Seller_Name) VALUES
(1, 'NAME1'),
(2, 'NAME2');

INSERT INTO BOOK (product_id, name, product_price, seller_id)
VALUES
(1, 'book1', 10.99, 1),
(2, 'book2', 15.99, 2);
