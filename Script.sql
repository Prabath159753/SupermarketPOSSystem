/**
  * @ author : Kavishka Prabath
  * @ since : 0.1.0
**/

DROP DATABASE IF EXISTS supermarket;
CREATE DATABASE IF NOT EXISTS supermarket;
SHOW DATABASES ;
USE supermarket;

DROP TABLE IF EXISTS customer;
CREATE TABLE IF NOT EXISTS customer(
    custId VARCHAR(6) NOT NULL,
    custTitle VARCHAR(5) NOT NULL,
    custName VARCHAR(30) NOT NULL DEFAULT 'Unknown',
    custAddress VARCHAR(30) NOT NULL,
    city VARCHAR(20) NOT NULL,
    province VARCHAR(20) NOT NULL,
    postalCode VARCHAR(9) NOT NULL,
    CONSTRAINT PRIMARY KEY (custId)
);
SHOW TABLES ;
DESCRIBE customer;

DROP TABLE IF EXISTS orders;
CREATE TABLE IF NOT EXISTS orders(
    orderId VARCHAR(15) NOT NULL ,
    custId VARCHAR(15) NOT NULL ,
    orderDate DATE DEFAULT NULL,
    orderTime TIME,
    total DECIMAL(8,2),
    CONSTRAINT PRIMARY KEY (orderId),
    CONSTRAINT FOREIGN KEY (custId) REFERENCES customer (custId) ON DELETE CASCADE ON UPDATE CASCADE
);
SHOW TABLES ;
DESCRIBE orders;

DROP TABLE IF EXISTS item;
CREATE TABLE IF NOT EXISTS item(
    itemCode VARCHAR(6) NOT NULL ,
    description VARCHAR(50) NOT NULL ,
    packSize VARCHAR(20) NOT NULL ,
    unitPrice DECIMAL(10,2) NOT NULL ,
    qtyOnHand INT(5) NOT NULL ,
    discount INT(5) NOT NULL DEFAULT 0,
    CONSTRAINT PRIMARY KEY (itemCode)
);
SHOW TABLES ;
DESCRIBE item;

DROP TABLE IF EXISTS order_detail;
CREATE TABLE IF NOT EXISTS order_detail(
    orderId VARCHAR(15) NOT NULL ,
    itemCode VARCHAR(15) NOT NULL ,
    orderQty INT(11) NOT NULL ,
    discount DECIMAL(6,2) NOT NULL ,
    unitPrice decimal(10,2) DEFAULT NULL,
    CONSTRAINT PRIMARY KEY (orderId, itemCode),
    CONSTRAINT FOREIGN KEY (itemCode) REFERENCES item (itemCode) ON DELETE CASCADE ON UPDATE CASCADE ,
    CONSTRAINT FOREIGN KEY (orderId) REFERENCES orders (orderId) ON DELETE CASCADE ON UPDATE CASCADE
);
SHOW TABLES ;
DESCRIBE order_detail;