CREATE DATABASE IF NOT EXISTS cine_irisperez;
USE cine_irisperez;

CREATE TABLE categorias (
idCategoria INT PRIMARY KEY,
nombre VARCHAR(100)
);

CREATE TABLE peliculas (
idPelicula INT PRIMARY KEY,
titulo VARCHAR(150),
director VARCHAR(100),
duracionMinutos INT,
idioma VARCHAR(50),
anoLanzamiento INT,
idCategoria INT,
CONSTRAINT fk_idCategoria FOREIGN KEY (idCategoria) REFERENCES categorias (idCategoria)
);


/* INSERCIÓN DE DATOS DE PRUEBA */

-- Insertar categorías
INSERT INTO categorias (idCategoria, nombre) VALUES
(1, 'Acción'),
(2, 'Comedia'),
(3, 'Drama'),
(4, 'Terror'),
(5, 'Ciencia Ficción');

-- Insertar películas
INSERT INTO peliculas (idPelicula, titulo, director, duracionMinutos, idioma, anoLanzamiento, idCategoria) VALUES
(1, 'Misión Imposible', 'Brian De Palma', 120, 'Inglés', 1996, 1),
(2, 'El Gran Lebowski', 'Joel Coen', 117, 'Inglés', 1998, 2),
(3, 'Forrest Gump', 'Robert Zemeckis', 142, 'Inglés', 1994, 3),
(4, 'El Conjuro', 'James Wan', 112, 'Inglés', 2013, 4),
(5, 'Interstellar', 'Christopher Nolan', 169, 'Inglés', 2014, 5);

SELECT p.idPelicula, p.titulo, p.director, p.duracionMinutos,
	p.idioma, p.anoLanzamiento, c.nombre AS categoria, c.idCategoria
FROM peliculas p
JOIN categorias c ON p.idCategoria = c.idCategoria;