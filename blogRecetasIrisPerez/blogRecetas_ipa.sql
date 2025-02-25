CREATE DATABASE IF NOT EXISTS blogRecetas_ipa;
USE blogRecetas_ipa;

CREATE TABLE recetas (
idReceta INT PRIMARY KEY AUTO_INCREMENT,
nombre VARCHAR(100),
ingredientes TEXT,
preparacion TEXT
);