<?php
require_once __DIR__ . '/../config/class_conexion.php';

class Receta {
    private $conexion;

    public function __construct() {
        $this->conexion = new Conexion();
    }


    // Método para REGISTRAR una receta en la base de datos

    public function registrarReceta($nombreReceta, $ingredientes, $preparacion) {
        // Ejecuto la sentencia para insertar la receta en la base de datos
        $query = "INSERT INTO recetas (nombre, ingredientes, preparacion) VALUES (?, ?, ?)";
        // Preparo la sentencia
        $sentencia = $this->conexion->conexion->prepare($query);
        // Uno los parámetros recibidos con la sentencia
        $sentencia->bind_param("sss", $nombreReceta, $ingredientes, $preparacion);
        // Ejecuto la sentencia
        $sentencia->execute();
        // Cierro la sentencia
        $sentencia->close();
    }


    // Método para OBTENER recetas de la base de datos por su nombre

    public function obtenerRecetas($nombreReceta) {
        // Ejecuto la sentencia SELECT para obtener las recetas con ese nombre de la base de datos
        // Uso LIKE para buscar recetas que contengan el nombre de receta buscado por el usuario
        // Ordeno las recetas por idReceta de forma descendente para que aparezcan primero las más recientes
        $query = "SELECT * FROM recetas WHERE nombre LIKE ? ORDER BY idReceta DESC";
        // Preparo la sentencia
        $sentencia = $this->conexion->conexion->prepare($query);
        // Añado '%' al nombre de la receta para que el SELECT busque recetas que contengan ese nombre
        $nombreReceta = "%$nombreReceta%";
        // Uno el parámetros recibido (nombre de la receta) con la sentencia
        $sentencia->bind_param("s", $nombreReceta);
        // Ejecuto la sentencia
        $sentencia->execute();
        // Almaceno el resultado de la sentencia en una variable
        $resultado = $sentencia->get_result();
        // Cierro la sentencia
        $sentencia->close();
        // Devuelvo el resultado de la sentencia
        return $resultado->fetch_all(MYSQLI_ASSOC);
    }


    // Método para EDITAR una receta

    public function editarReceta($idReceta, $nombreReceta, $ingredientes, $preparacion) {
        // Ejecuto la sentencia UPDATE que actualiza los datos de la receta en la base de datos
        $query = "UPDATE recetas SET nombre = ?, ingredientes = ?, preparacion = ? WHERE idReceta = ?";
        $sentencia = $this->conexion->conexion->prepare($query);
        $sentencia->bind_param("sssi", $nombreReceta, $ingredientes, $preparacion, $idReceta);
        $sentencia->execute();
        $sentencia->close();
    }

    // Método que recibe el id de una receta y devuelve todos los datos de la receta
    public function obtenerRecetaPorId($idReceta) {
        // Ejecuto el SELECT que obtiene todos los datos de la receta con id: $idReceta
        $query = 'SELECT * FROM recetas WHERE idReceta = ?';
        $sentencia = $this->conexion->conexion->prepare($query);
        $sentencia->bind_param("i", $idReceta);
        $sentencia->execute();
        // Obtengo el resultado de la sentencia
        $resultado = $sentencia->get_result();
        // Cierro la sentencia
        $sentencia->close();
        // Devuelvo el resultado de la sentencia (datos de la receta)
        return $resultado->fetch_assoc();
    }


    // Método para ELIMINAR una Receta

    public function eliminarReceta($idReceta) {
        // Ejecuto la sentencia que elimina la receta de la base de datos
        $query = "DELETE FROM recetas WHERE idReceta = ?";
        $sentencia = $this->conexion->conexion->prepare($query);
        $sentencia->bind_param("i", $idReceta);
        $sentencia->execute();
        $sentencia->close();
    }
}
?>