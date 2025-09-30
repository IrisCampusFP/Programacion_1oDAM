<?php
require_once '../modelo/class_usuario.php';

class UsuariosController {
    private $usuario;

    public function __construct() {
        $this->usuario = new Usuario();
    }

    // Método llama al método obtenerUsuarios() y devuelve su resultado (todos los datos de todos los usuarios)
    public function listarUsuarios() {
        return $this->usuario->obtenerUsuarios();
    }

    // Método que llama al método agregarUsuario() (registra un usuario nuevo insertando sus datos en la base de datos)
    public function agregarUsuario($nombre, $apellidos, $edad, $email, $clave, $idPlan, $duracionSuscripcion) {
        $this->usuario->agregarUsuario($nombre, $apellidos, $edad, $email, $clave, $idPlan, $duracionSuscripcion);
    }

    // Método que llama al método obtenerUsuarioPorId() y devuelve su resultado (todos los datos del usuario con ese id)
    public function obtenerUsuarioPorId($idUsuario) {
        return $this->usuario->obtenerUsuarioPorId($idUsuario);
    }

    // Método que llama al método obtenerPaquetesUsuario y devuelve su resultado (todos los paquetes contratados por el usuario con ese id)
    public function obtenerPaquetesUsuario($idUsuario){
        return $this->usuario->obtenerPaquetesUsuario($idUsuario);
    }

    // Método que llama al método actualizarUsuario() (actualiza los datos del usuario registrado con ese id)
    public function actualizarUsuario($idUsuario, $nombre, $apellidos, $edad, $email, $clave, $fechaSuscripcion, $duracionSuscripcion) {
        $this->usuario->actualizarUsuario($idUsuario, $nombre, $apellidos, $edad, $email, $clave, $fechaSuscripcion, $duracionSuscripcion);
    }

    // Método que llama al método actualizarPlan() (cambia el plan del usuario con ese id al plan que elija)
    public function actualizarPlan($idUsuario, $idPlan){
        $this->usuario->actualizarPlan($idUsuario, $idPlan);
    }

    // Método que llama al método actualizarPaquetes() (elimina los paquetes anteriores del usuario y establece los nuevos)
    public function actualizarPaquetes($idUsuario, $idPaquetes){
        $this->usuario->actualizarPaquetes($idUsuario, $idPaquetes);
    }

    // Método que llama al método eliminarUsuario() (elimina el usuario con ese id)
    public function eliminarUsuario($idUsuario) {
        $this->usuario->eliminarUsuario($idUsuario);
    }

} 

// Si se reciben datos de un formulario mediante el método 'POST':
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    // Instancio un objeto (controller) de la clase UsuariosController()
    $controller = new UsuariosController();

    // Si la accion es registrar -> datos recibidos del formulario del archivo registrarUsuario.php
    if ($_POST['accion'] == 'registrar'){
        // Llamo al método agregarUsuario() enviando los datos introducidos en el formulario al controlador
        $controller->agregarUsuario(
            $_POST['nombre'],
            $_POST['apellidos'],
            $_POST['edad'],
            $_POST['email'],
            $_POST['clave'],
            $_POST['idPlan'],
            $_POST['duracionSuscripcion']
        );
        // Cuando se termine de ejecutar el método en el controlador, redirijo al usuario a la lista de usuarios
        header("Location: ../vista/listaUsuarios.php");
        exit();
    }

    // Si la accion es actualizarUsuario -> datos recibidos del formulario del archivo actualizarUsuario.php
    elseif ($_POST['accion'] == 'actualizarUsuario'){
        // Llamo al método actualizarUsuario() enviando los datos introducidos en el formulario al controlador
        $controller->actualizarUsuario(
            $_POST['idUsuario'],
            $_POST['nombre'], 
            $_POST['apellidos'], 
            $_POST['edad'], 
            $_POST['email'], 
            $_POST['clave'],
            $_POST['fechaSuscripcion'],
            $_POST['duracionSuscripcion']
        );
        // Cuando se termine de ejecutar el método en el controlador, redirijo al usuario a la lista de usuarios
        header("Location: ../vista/listaUsuarios.php");
        exit();
    }

    // Si la accion es actualizarPlan -> datos recibidos del formulario del archivo actualizarPlan.php)
    elseif ($_POST['accion'] == 'actualizarPlan'){
        // Llamo al método actualizarPlan() enviando los datos introducidos en el formulario al controlador
        $controller->actualizarPlan($_POST['idUsuario'],$_POST['idPlan']);
        
        // Cuando se termine de ejecutar el método en el controlador, redirijo al usuario a la lista de usuarios
        header("Location: ../vista/listaUsuarios.php");
        exit();
    }

    // Si la accion es actualizarPaquetes -> datos recibidos del formulario del archivo actualizarPaquetes.php
    elseif ($_POST['accion'] == 'actualizarPaquetes'){
        // Introduzco los ids de los paquetes seleccionados en un array
        $idPaquetes = $_POST['idPaquetes'] ?? []; // (Si el usuario no selecciona ningún paquete se guardará el array vacío)
        // Llamo al método actualizarPaquetes() enviando los datos recibidos del formulario al controlador
        $controller->actualizarPaquetes($_POST['idUsuario'], $idPaquetes);

        // Cuando se termine de ejecutar el método en el controlador, redirijo al usuario a la lista de usuarios
        header("Location: ../vista/listaUsuarios.php");
        exit();
    }

    // Si la accion es eliminarUsuario -> datos recibidos del formulario del archivo eliminarUsuario.php
    elseif ($_POST['accion'] == 'eliminarUsuario'){
        // Llamo al método eliminarUsuario() enviando el id recibido al controlador
        $controller->eliminarUsuario($_POST['idUsuario']);
        
        // Cuando se termine de ejecutar el método en el controlador, redirijo al usuario a la lista de usuarios
        header("Location: ../vista/listaUsuarios.php");
        exit();
    }
}
?>