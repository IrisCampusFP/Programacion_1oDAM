CREATE DATABASE IF NOT EXISTS vlogRecetas_ipa;
USE vlogRecetas_ipa;

CREATE TABLE recetas (
idReceta INT PRIMARY KEY AUTO_INCREMENT,
nombre VARCHAR(100),
ingredientes TEXT,
preparacion TEXT
);