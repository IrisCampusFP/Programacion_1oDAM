<?php
require_once '../config/class_conexion.php';

class Usuario {
    private $conexion;

    public function __construct() {
        $this->conexion = new Conexion();
    }

    // Método para registrar un usuario nuevo en la base de datos
    public function agregarUsuario($nombreUsuario, $correo, $clave) {
        // Ejecuto la sentencia para insertar al usuario en la base de datos
        $query = "INSERT INTO usuarios (nombreUsuario, correo, clave) VALUES (?, ?, ?)";
        // Preparo la sentencia
        $sentencia = $this->conexion->conexion->prepare($query);
        
        // Almaceno la contraseña del usuario de manera segura (con hash)
        $claveHasheada = password_hash($clave, PASSWORD_BCRYPT);
        // Uno los parámetros recibidos con la sentencia
        $sentencia->bind_param("sss", $nombreUsuario, $correo, $claveHasheada);

        // Ejecuto la sentencia
        $sentencia->execute();

        // Obtengo el id del usuario insertado
        $id = $this->conexion->conexion->insert_id;

        // Cierro la sentencia
        $sentencia->close();

        // Inicio la sesión
        session_start();
        // Almaceno el id del usuario en la sesión
        $_SESSION['idUsuario'] = $id;

        // Concedo acceso al usuario a la página web
        header("Location: ../vista/listaTareas.php");
        exit();
    }


    // Método que concede el acceso a un usuario ya registrado
    public function iniciarSesion($correo, $clave) {
        // Ejecuto la sentencia para obtener al usuario de la base de datos
        $query = "SELECT * FROM usuarios WHERE correo = ?";
        $sentencia = $this->conexion->conexion->prepare($query);
        $sentencia->bind_param("s", $correo);
        $sentencia->execute();

        // Almaceno el resultado de la sentencia en una variable
        $resultado = $sentencia->get_result();
        // Almaceno los datos del usuario en un array
        $usuario = $resultado->fetch_assoc();
        
        $sentencia->close();

        // Compruebo si el usuario con el correo introducido existe
        if ($usuario) {
            // Si la contraseña es correcta, inicio la sesión
            if (password_verify($clave, $usuario['clave'])) {
                // Inicio la sesión
                session_start();
                // Almaceno el id del usuario en la sesión
                $_SESSION['idUsuario'] = $usuario['idUsuario'];
                // Concedo el acceso a la página al usuario
                header("Location: ../vista/listaTareas.php");
                exit();
            // Si la contraseña no coincide, se lo indico al usuario
            } else {
                $mensajeError = "Error al iniciar sesión, contraseña incorrecta ❌.";
                header("Location: ../vista/iniciarSesion.php?mensaje=" . urlencode($mensajeError));
            }
        // Si el correo introducido no está registrado, se lo indico al usuario
        } else {
            $mensajeError = "Error: el correo electrónico que has introducido no está registrado. Prueba a registrarte.";
            header("Location: ../vista/iniciarSesion.php?mensaje=" . urlencode($mensajeError));
        }
    }

    // Método para obtener todas las tareas del usuario
    public function obtenerTareasUsuario($idUsuario) {
        // Ejecuto la sentencia para obtener todas las tareas del usuario
        $query = "SELECT * FROM tareas WHERE idUsuario = ? ORDER BY estado DESC";
        $sentencia = $this->conexion->conexion->prepare($query);
        $sentencia->bind_param("i", $idUsuario);
        $sentencia->execute();

        $resultado = $sentencia->get_result();

        $sentencia->close();

        // El método fetch_all() devuelve un array con todas las tareas del usuario
        return $resultado->fetch_all(MYSQLI_ASSOC);
    }


    // Método para agregar una nueva tarea a la base de datos
    public function agregarTarea($nombreTarea, $descripcion, $fechaLimite) {
        session_start();
        // Ejecuto la sentencia que inserta la tarea en la base de datos
        $query = "INSERT INTO tareas (nombreTarea, descripcion, fechaCreacion, fechaLimite, idUsuario) 
            VALUES (?, ?, NOW(), ?, ?)";
        $sentencia = $this->conexion->conexion->prepare($query);
        $sentencia->bind_param("sssi", $nombreTarea, $descripcion, $fechaLimite, $_SESSION['idUsuario']);
        // (s = string, i = int)

        $sentencia->execute();

        $sentencia->close();
    }


    // Método para modificar el estado de una tarea a 'Completada'
    public function marcarTareaCompletada($idTarea) {
        // Ejecuto la sentencia que actualiza el estado de la tarea a 'Completada'
        $query = "UPDATE tareas SET estado = 'Completada' WHERE idTarea = ?";
        $sentencia = $this->conexion->conexion->prepare($query);
        $sentencia->bind_param("i", $idTarea);
        $sentencia->execute();
        $sentencia->close();
    }


    // Método para eliminar una tarea
    public function eliminarTarea($idTarea) {
        // Ejecuto la sentencia que elimina la tarea de la base de datos
        $query = "DELETE FROM tareas WHERE idTarea = ?";
        $sentencia = $this->conexion->conexion->prepare($query);
        $sentencia->bind_param("i", $idTarea);
        $sentencia->execute();
        $sentencia->close();
    }


    // Método para obtener todos los datos de una tarea por su id
    public function obtenerTareaPorId($idTarea) {
        // Ejecuto la sentencia para obtener la tarea de la base de datos
        $query = "SELECT * FROM tareas WHERE idTarea = ?";
        $sentencia = $this->conexion->conexion->prepare($query);
        $sentencia->bind_param("i", $idTarea);
        $sentencia->execute();

        $resultado = $sentencia->get_result();
        $sentencia->close();
        return $resultado->fetch_assoc();
    }

    // Método para editar una tarea existente
    public function editarTarea($nombreTarea, $descripcion, $fechaLimite, $estado, $idTarea) {
        // Ejecuto una sentencia que actualiza los datos de la tarea en la base de datos
        $query = "UPDATE tareas SET nombreTarea = ?, descripcion = ?, fechaLimite = ?, estado = ? WHERE idTarea = ?";
        $sentencia = $this->conexion->conexion->prepare($query);
        $sentencia->bind_param("ssssi", $nombreTarea, $descripcion, $fechaLimite, $estado, $idTarea);
        $sentencia->execute();
        $sentencia->close();
    }
}
?>