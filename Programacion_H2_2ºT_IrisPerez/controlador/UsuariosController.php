<?php
require_once '../modelo/class_usuario.php';

class UsuariosController {
    private $usuario;

    public function __construct() {
        $this->usuario = new Usuario();
    }

    public function agregarUsuario($nombreUsuario, $correo, $clave) {
        $this->usuario->agregarUsuario($nombreUsuario, $correo, $clave);
    }

    public function iniciarSesion($correo, $clave) {
        return $this->usuario->iniciarSesion($correo, $clave);
    }

    public function obtenerTareasUsuario($idUsuario) {
        return $this->usuario->obtenerTareasUsuario($idUsuario);
    }

    public function agregarTarea($nombreTarea, $descripcion, $fechaLimite) {
        $this->usuario->agregarTarea($nombreTarea, $descripcion, $fechaLimite);
    }

    public function marcarTareaCompletada($idTarea) {
        $this->usuario->marcarTareaCompletada($idTarea);
    }

    public function eliminarTarea($idTarea) {
        $this->usuario->eliminarTarea($idTarea);
    }


    public function obtenerTareaPorId($idTarea) {
        return $this->usuario->obtenerTareaPorId($idTarea);
    }

    public function editarTarea($nombreTarea, $descripcion, $fechaLimite, $estado, $idTarea) {
        $this->usuario->editarTarea($nombreTarea, $descripcion, $fechaLimite, $estado, $idTarea);
    }
} 

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $controller = new UsuariosController();

    /* Para cada acción, paso los valores recibidos del formulario al método 
    correspondiente para así ejecutar la acción deseada en la base de datos,
    después, redirijo al usuario con header. */

    if ($_POST['accion'] == 'registrar') {
        $controller->agregarUsuario(
            $_POST['nombreUsuario'],
            $_POST['correo'],
            $_POST['clave']
        );
    }
    
    elseif ($_POST['accion'] == 'iniciarSesion') {
        $controller->iniciarSesion($_POST['correo'], $_POST['clave']);
    }
    
    elseif ($_POST['accion'] == 'agregarTarea') {
        /* Si la tarea no tiene fecha límite, establezco el campo en null,
            ya que si se pasa el campo vacío saltará un error */
        $fechaLimite = !empty($_POST['fechaLimite']) ? $_POST['fechaLimite'] : null;
        $controller->agregarTarea(
            $_POST['nombreTarea'], 
            $_POST['descripcion'], 
            $fechaLimite
        );
        header("Location: ../vista/listaTareas.php");
        exit();
    }

    elseif ($_POST['accion'] == 'completarTarea') {
        $idTarea = $_POST['idTarea'];
        $controller->marcarTareaCompletada($idTarea);
        header("Location: ../vista/listaTareas.php");
        exit();
    }

    elseif ($_POST['accion'] == 'eliminarTarea') {
        $idTarea = $_POST['idTarea'];
        $controller->eliminarTarea($idTarea);
        header("Location: ../vista/listaTareas.php");
        exit();
    }

    elseif ($_POST['accion'] == 'editarTarea') {
        // Si la tarea no tiene fecha límite, establezco el campo en null
        $fechaLimite = !empty($_POST['fechaLimite']) ? $_POST['fechaLimite'] : null;
        $controller->editarTarea(
            $_POST['nombreTarea'],
            $_POST['descripcion'],
            $fechaLimite,
            $_POST['estado'],
            $_POST['idTarea']
        );
        header("Location: ../vista/listaTareas.php");
        exit();
    }
}
?>