-- Active: 1732127790281@@mysql-1fcc4b03-cristian-4d9d.l.aivencloud.com@18115@CARVIA
-- Active: 1732127790281@@mysql-1fcc4b03-cristian-4d9d.l.aivencloud.com@18115@information_schema
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
    mileage INT,
    fuel_type VARCHAR(50),
    transmission VARCHAR(50)

);

    user_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users(id)

Usuario, cuenta, mensajes, anuncios, vehiculos, compras y pagos

CREATE TABLE Anuncios (
    id INT PRIMARY KEY AUTO_INCREMENT,
    fecha DATE NOT NULL,
    descripcion VARCHAR(255) NOT NULL,
    precio DOUBLE NOT NULL,
    urlFoto VARCHAR(2083) NOT NULL
);

