CREATE DATABASE IF NOT EXISTS sistemaGestionTareas_ipa;

USE sistemaGestionTareas_ipa;

CREATE TABLE usuarios (
idUsuario INT PRIMARY KEY AUTO_INCREMENT,
nombreUsuario VARCHAR(50),
correo VARCHAR(100) UNIQUE,
clave VARCHAR(255)
);

CREATE TABLE tareas (
idTarea INT PRIMARY KEY AUTO_INCREMENT,
nombreTarea VARCHAR(150),
descripcion TEXT,
estado ENUM('Pendiente', 'Completada') DEFAULT 'Pendiente',
fechaCreacion DATETIME,
fechaLimite DATETIME,
idUsuario INT,
CONSTRAINT fk_idUsuario FOREIGN KEY (idUsuario) REFERENCES usuarios (idUsuario) ON DELETE CASCADE ON UPDATE CASCADE
);


select*from usuarios left join tareas on usuarios.idusuario = tareas.idusuario;