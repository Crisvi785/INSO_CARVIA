CREATE DATABASE IF NOT EXISTS CARVIA;
USE CARVIA;

CREATE TABLE IF NOT EXISTS Users (
	id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    full_name VARCHAR(255) NULL,
    email VARCHAR(100) NULL,
    password VARCHAR(255) NULL
);


CREATE TABLE IF NOT EXISTS Vehicles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    make VARCHAR(100) NOT NULL,
    model VARCHAR(100) NOT NULL,
    year INT NOT NULL,
    color VARCHAR(100) NOT NULL,
    mileage INT,
    fuel_type VARCHAR(50),
    transmission VARCHAR(50),
    price DECIMAL(10, 2),
    description TEXT,
    image_url VARCHAR(255),
    status VARCHAR(50),
    date_added DATETIME DEFAULT CURRENT_TIMESTAMP,
    location VARCHAR(100),
    user_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users(id)
);

