<?php
require_once '../config/class_conexion.php';

class Usuario {
    // Creo el atributo conexion para conectar la base de datos
    private $conexion;

    // Creo una conexión con la base de datos mediante el constructor y la guardo en el atributo conexion
    public function __construct() {
        $this->conexion = new Conexion();
    }

    // Método que obtiene todos los datos de todos los usuarios mediante una consulta sql
    public function obtenerUsuarios() {
        // Consulta a la base de datos para mostrar la tabla con todos los datos de los usuarios
        $query = "SELECT u.idUsuario, u.nombre, u.apellidos, u.edad, u.email, u.clave, u.duracionSuscripcion, 
            u.fechaSuscripcion, CONCAT(pl.plan, ' (', pl.precio, '€)') AS plan,
            GROUP_CONCAT(CONCAT(pa.paquete, ' (', pa.precio, '€)') SEPARATOR ', ') AS paquetesAdicionales, 
            (pl.precio + IFNULL(SUM(pa.precio), 0)) AS costoTotal
        FROM usuarios u
        LEFT JOIN planes pl ON u.idPlan = pl.idPlan
        LEFT JOIN usuariosPaquetes up ON u.idUsuario = up.idUsuario
        LEFT JOIN paquetes pa ON up.idPaquete = pa.idPaquete
        GROUP BY u.idUsuario, u.nombre, u.apellidos, u.edad, u.email, u.clave, 
            u.fechaSuscripcion, u.duracionSuscripcion, pl.plan, pl.precio;";

        // Ejecuto la consulta y obtengo el resultado de la base de datos, guardándolo en una variable
        $resultado = $this->conexion->conexion->query($query);
        // Inicializo el array usuarios
        $usuarios = [];
        // Introduzco los datos obtenidos en el array
        while ($fila = $resultado->fetch_assoc()) {
            $usuarios[] = $fila;
        }
        // Cierro el resultado de la consulta para liberar memoria
        $resultado->close();
        // Devuelvo el array con todos los datos de todos los usuarios
        return $usuarios;
    }

    // Método que agrega un usuario a la base de datos registrando todos sus datos mediante un INSERT INTO 
    public function agregarUsuario($nombre, $apellidos, $edad, $email, $clave, $idPlan, $duracionSuscripcion) {
        // Sentencia INSERT INTO que registra al usuario con todos sus datos y establece la fecha de suscripción con NOW() (fecha y hora del momento en el que se ejecuta)
        $query = "INSERT INTO usuarios (nombre, apellidos, edad, email, clave, fechaSuscripcion, duracionSuscripcion, idPlan) 
            VALUES (?, ?, ?, ?, ?, NOW(), ?, ?)";
        // Preparo la sentencia para su ejecución en la base de datos y la guardo en una variable
        $sentencia = $this->conexion->conexion->prepare($query);
        // Encripto la contraseña del usuario para guardarla de forma segura
        $clavehash = password_hash($clave, PASSWORD_BCRYPT);
        // Uno los datos introducidos por el usuario a la sentencia (INSERT INTO) para registrarlos
        $sentencia->bind_param("ssisssi", $nombre, $apellidos, $edad, $email, $clavehash, $duracionSuscripcion, $idPlan);
        // (s = string, i = int)

        // Ejecuto la sentencia, registrando al usuario e insertando sus datos en la base de datos
        $sentencia->execute();

        // Cierro la sentencia para liberar memoria
        $sentencia->close();
    }

    // Método que obtiene todos los datos de un usuario mediante su id
    public function obtenerUsuarioPorId($idUsuario) {
        // Consulta sql que obtiene todos los datos de un usuario mediante su id
        $query = "SELECT * FROM usuarios WHERE idUsuario = ?";
        // Preparo la consulta y la guardo en una variable ($sentencia)
        $sentencia = $this->conexion->conexion->prepare($query);
        // Uno el id de usuario recibido con la consulta preparada
        $sentencia->bind_param("i", $idUsuario); // (i = int)
        // Ejecuto la consulta en la base de datos
        $sentencia->execute();
        // Obtengo el resultado de la consulta en una variable
        $resultado = $sentencia->get_result();

        // Cierro la sentencia para liberar memoria
        $sentencia->close();
        // Devuelvo todos los datos del usuario con fetch_assoc()
        return $resultado->fetch_assoc();
    }

    // Método que actualiza los datos de un usuario
    public function actualizarUsuario($idUsuario, $nombre, $apellidos, $edad, $email, $clave, $fechaSuscripcion, $duracionSuscripcion) {
        // Sentencia sql que actualiza los datos del usuario registrando los datos recibidos
        $query = "UPDATE usuarios SET nombre = ?, apellidos = ?, edad = ?, email = ?, clave = ?, fechaSuscripcion = ?, duracionSuscripcion = ? WHERE idUsuario = ?";
        // Preparo la sentencia y la guardo en una variable
        $sentencia = $this->conexion->conexion->prepare($query);
        // Uno los datos introducidos por el usuario con la sentencia preparada
        $sentencia->bind_param("ssissssi", $nombre, $apellidos, $edad, $email, password_hash($clave, PASSWORD_BCRYPT), $fechaSuscripcion, $duracionSuscripcion, $idUsuario);
        // (s = string, i = int)

        // Ejecuto la sentencia en la base de datos
        $sentencia->execute();
        // Cierro la sentencia para liberar memoria
        $sentencia->close();
    }

    // Método que actualiza el plan del usuario recibiendo su id y el id del nuevo plan
    public function actualizarPlan($idUsuario, $idPlan){
        // Sentencia sql que actualiza el plan del usuario y reestablece su fecha de suscripción, registrando la fecha y hora en que se realiza el cambio
        $query = "UPDATE usuarios SET idPlan = ?, fechaSuscripcion = NOW() WHERE idUsuario = ?";
        // Preparo la sentencia y la guardo en una variable
        $sentencia = $this->conexion->conexion->prepare($query);
        // Uno los ids recibidos con la sentencia preparada
        $sentencia->bind_param("ii", $idPlan, $idUsuario);
        // Ejecuto la sentencia en la base de datos
        $sentencia->execute();
        // Cierro la sentencia para liberar memoria
        $sentencia->close();
    }

    // Método que obtiene los paquetes ya contratados por el usuario mediante su id
    public function obtenerPaquetesUsuario($idUsuario){
        // Consulta sql que obtiene los paquetes contratados por un usuario
        $query = "SELECT idPaquete FROM usuariosPaquetes WHERE idUsuario = ?";
        // Preparo la consulta y la guardo en una variable
        $sentencia = $this->conexion->conexion->prepare($query);
        // Uno el id de usuario recibido a la consulta preparada
        $sentencia->bind_param("i", $idUsuario);
        // Ejecuto la consulta en la base de datos
        $sentencia->execute();
        // Obtengo los resultados de la consulta en una variable
        $resultado = $sentencia->get_result();

        // Creo un array para almacenar los ids de los paquetes contratados por el usuario
        $paquetesUsuario = [];
        // Guardo en el array los ids de los paquetes que tenga contratados el usuario
        while ($fila = $resultado->fetch_assoc()){
            $paquetesUsuario[] = $fila['idPaquete'];
        }
        // Cierro la sentencia para liberar memoria
        $sentencia->close();
        // Devuelvo los ids de los paquetes contratados por el usuario
        return $paquetesUsuario;
    }

    // Método que actualiza los paquetes contratados por un usuario
    public function actualizarPaquetes($idUsuario, $idPaquetes){
        // Elimino los paquetes contratados anteriormente:
        // Sentencia sql que elimina todos los paquetes contratados por el usuario
        $query = "DELETE FROM usuariosPaquetes WHERE idUsuario = ?";
        // Preparo la sentencia y la guardo en una variable
        $sentencia = $this->conexion->conexion->prepare($query);
        // Uno el id de usuario recibido con la sentencia preparada
        $sentencia->bind_param("i", $idUsuario);
        // Ejecuto la sentencia en la base de datos
        $sentencia->execute();

        // Inserto los paquetes seleccionados:
        // Para cada paquete seleccionado:
        foreach ($idPaquetes as $idPaquete) {
            // Sentencia sql que registra el paquete seleccionado por el usuario actualizando la fecha de contratación con NOW() (fecha y hora del momento en el que se ejecuta el insert)
            $query = "INSERT INTO usuariosPaquetes (idUsuario, idPaquete, fechaContratacion) 
            VALUES (?, ?, NOW())";
            // Preparo la sentencia y la guardo en una variable
            $sentencia = $this->conexion->conexion->prepare($query);
            // Uno el id de usuario y el id del paquete con la sentencia preparada
            $sentencia->bind_param("ii", $idUsuario, $idPaquete);
            // Ejecuto la sentencia en la base de datos
            $sentencia->execute();
            // Cierro la sentencia para liberar memoria
            $sentencia->close();
        }
    }

    // Método que elimina un usuario mediante su id
    public function eliminarUsuario($idUsuario) {
        // Sentencia sql que elimina un usuario mediante su id
        $query = "DELETE FROM usuarios WHERE idUsuario = ?";
        // Preparo la sentencia y la guardo en una variable
        $sentencia = $this->conexion->conexion->prepare($query);
        // Uno la sentencia preparada con el id de usuario obtenido
        $sentencia->bind_param("i", $idUsuario);
        // Ejecuto la sentencia en la base de datos, eliminando el usuario con idUsuario = $idUsuario
        $sentencia->execute();
        // Cierro la sentencia para liberar memoria
        $sentencia->close();
    }
}
?>