CREATE DATABASE IF NOT EXISTS quantityDB;

USE quantityDB;

CREATE TABLE IF NOT EXISTS quantity_log(
    id INT AUTO_INCREMENT PRIMARY KEY,
    operation VARCHAR(20),
    input1 VARCHAR(100),
    input2 VARCHAR(100),
    result VARCHAR(20)
);