DROP TABLE Product IF EXISTS;
DROP TABLE Seller IF EXISTS;
CREATE TABLE Product (
    Product_id int primary key,
    name varchar(255) not null,
    Product_Price decimal (10,2) not null CHECK (product_price > 0 ),
    Seller_id int references Seller(Seller_id)
);
CREATE TABLE Seller (
    Seller_id int primary key,
    Seller_Name varchar(255) not null unique,
);