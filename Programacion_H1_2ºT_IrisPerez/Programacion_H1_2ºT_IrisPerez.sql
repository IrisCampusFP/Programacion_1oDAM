CREATE DATABASE IF NOT EXISTS streamweb_ipa;

USE streamweb_ipa;


CREATE TABLE planes (
idPlan INT PRIMARY KEY AUTO_INCREMENT,
plan VARCHAR(100),
precio DECIMAL(10,2)
);

CREATE TABLE usuarios (
idUsuario INT PRIMARY KEY AUTO_INCREMENT,
nombre VARCHAR(150),
apellidos VARCHAR(150),
edad INT,
email VARCHAR(100) UNIQUE,
clave VARCHAR(255),
fechaSuscripcion DATETIME,
duracionSuscripcion ENUM("Mensual","Anual"),
idPlan INT,
CONSTRAINT fk_idPlan FOREIGN KEY (idPlan) REFERENCES planes(idPlan) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE paquetes (
idPaquete INT PRIMARY KEY AUTO_INCREMENT,
paquete VARCHAR(100),
precio DECIMAL(10,2)
);

CREATE TABLE usuariosPaquetes (
idUsuario INT,
idPaquete INT,
fechaContratacion DATETIME,
PRIMARY KEY (idUsuario, idPaquete),
CONSTRAINT fk_idUsuario FOREIGN KEY (idUsuario) REFERENCES usuarios(idUsuario) ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT fk_idPaquete FOREIGN KEY (idPaquete) REFERENCES paquetes(idPaquete) ON DELETE CASCADE ON UPDATE CASCADE
);


-- Inserto datos de prueba en todas las tablas

INSERT INTO planes (idPlan, plan, precio) VALUES
(1, 'Básico', 9.99),
(2, 'Estándar', 13.99),
(3, 'Premium', 17.99);

INSERT INTO paquetes (idPaquete, paquete, precio) VALUES
(1, 'Deporte', 6.99),
(2, 'Cine', 7.99),
(3, 'Infantil', 4.99);

-- Insertar usuarios con dos apellidos
INSERT INTO usuarios (nombre, apellidos, edad, email, clave, fechaSuscripcion, duracionSuscripcion, idPlan) VALUES
('Laura', 'Sánchez García', 29, 'laura.sanchez@email.com', '$2a$10$HzIs9X1dFgyC62Pqf9Mt7OaFuUSZG9sbI5gFw9jS2b9pFf47TYfVC', '2025-01-15 08:30:00', 'Mensual', 1),
('Miguel', 'Ramírez López', 33, 'miguel.ramirez@email.com', '$2a$10$Js1W1Fh8oQ8VZp6LrOaG4zHptO16OHz4QJHkn.n9m5VxEVTzihS9i', '2025-01-10 15:45:00', 'Anual', 2),
('Ana', 'Torres Martínez', 42, 'ana.torres@email.com', '$2a$10$dpSo2kM92AJSO6dDWprK5sYoG6EzshI2f/dJtbJtb3.6bz5yD/UW2', '2024-12-20 12:00:00', 'Mensual', 3),
('Luis', 'González Pérez', 26, 'luis.gonzalez@email.com', '$2a$10$ZYa6DTIZYAZTQUUsZ5g6jR0Z7WQ7DCvsB1F5HLQQfF0s8ylvXi6zO', '2025-01-20 19:20:00', 'Mensual', 1),
('Carmen', 'Martín Gómez', 36, 'carmen.martin@email.com', '$2a$10$52dJYrI91XB0wcpn.N9MZYhwLRkpHLaZg8I9g1hdtAYuT4fQKCN2W', '2025-01-25 09:10:00', 'Anual', 2),
('Javier', 'Fernández Ruiz', 31, 'javier.fernandez@email.com', '$2a$10$BMiGvBhH.y7pAlY8gK8b5.1wEVrUYZyJ3rS5QNzfs5hdT1XhHppIu', '2025-01-18 10:50:00', 'Mensual', 3),
('Patricia', 'López Díaz', 40, 'patricia.lopez@email.com', '$2a$10$kE7cPo1Qpc9wY2s7xowvdu6Wf9FlLjg6Fg2bffmUVBTHSlNBXauqq', '2025-01-22 14:35:00', 'Anual', 2),
('Fernando', 'García Fernández', 38, 'fernando.garcia@email.com', '$2a$10$XQe7TduIi0y3rnWc5Vcebm8ZZ3QUeJK33fh8rxu9Fv.TAfW6m4M66', '2025-01-30 11:40:00', 'Mensual', 1),
('Silvia', 'Álvarez Romero', 27, 'silvia.alvarez@email.com', '$2a$10$M7eHROcnSDE9ijcbSZrVGxTn4zLl7lxhtbhpfYjXs8NdHGLXwQtZG', '2025-01-17 16:25:00', 'Mensual', 3),
('Raúl', 'Ruiz Pérez', 30, 'raul.ruiz@email.com', '$2a$10$YlysyKl3XsFm9xHXhD.8j/sZpK78swfSXY2yWL.w19uK4elBmrA5e', '2025-01-12 20:05:00', 'Anual', 3);

INSERT INTO usuariosPaquetes (idUsuario, idPaquete, fechaContratacion) VALUES
(1, 1, '2025-01-15 08:30:00'),
(2, 2, '2025-01-10 15:45:00'),
(3, 1, '2024-12-20 12:00:00'),
(3, 3, '2024-12-20 12:00:00'),
(4, 2, '2025-01-20 19:20:00'),
(5, 3, '2025-01-25 09:10:00'),
(6, 1, '2025-01-18 10:50:00'),
(6, 2, '2025-01-18 10:50:00'),
(7, 1, '2025-01-22 14:35:00'),
(8, 3, '2025-01-30 11:40:00'),
(9, 2, '2025-01-17 16:25:00'),
(9, 1, '2025-01-17 16:25:00'),
(10, 1, '2025-01-12 20:05:00');

select*from usuariosPaquetes;

-- (Query para obtener todos los datos de los usuarios en una tabla)
SELECT u.idUsuario, u.nombre, u.apellidos, u.edad, u.email, u.clave, u.duracionSuscripcion, 
	u.fechaSuscripcion, CONCAT(pl.plan, ' (', pl.precio, '€)') AS plan,
    GROUP_CONCAT(CONCAT(pa.paquete, ' (', pa.precio, '€)') SEPARATOR ', ') AS paquetesAdicionales, 
    (pl.precio + IFNULL(SUM(pa.precio), 0)) AS costoTotal
FROM usuarios u
LEFT JOIN planes pl ON u.idPlan = pl.idPlan
LEFT JOIN usuariosPaquetes up ON u.idUsuario = up.idUsuario
LEFT JOIN paquetes pa ON up.idPaquete = pa.idPaquete
GROUP BY u.idUsuario, u.nombre, u.apellidos, u.edad, u.email, u.clave, 
	u.fechaSuscripcion, u.duracionSuscripcion, pl.plan, pl.precio;