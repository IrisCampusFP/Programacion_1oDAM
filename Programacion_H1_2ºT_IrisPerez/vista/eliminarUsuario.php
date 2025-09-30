<?php
// Incluyo el archivo UsuariosController.php de la carpeta controlador para poder usar sus métodos
require_once '../controlador/UsuariosController.php';

// Obtengo el id del usuario de la URL con el método 'GET' (el botón con el que se accede a esta página envía el id del usuario en el que se pulsa en la URL)
$id = $_GET['id'];

// Instancio un objeto de la clase UsuariosController()
$controller = new UsuariosController();
// Llamo al método obtenerUsuarioPorId() con el id del usuario (obtenido de la URL) para obtener todos sus datos y los guardo en la variable $usuario
$usuario = $controller->obtenerUsuarioPorId($id);
?>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Eliminar Usuario (StreamWeb)</title>
    <!-- Enlace a Bootstrap -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
    <div class="container" style="margin-top: 4.2%;">
        <h1 class="text-center mt-5">¿Estás segur@ de que quieres eliminar este usuario?</h1>
        <p class="text-center mt-4">Estos son los datos del usuario que estás a punto de eliminar:</p>
        <!-- Muestro los datos del usuario a eliminar en forma de formulario mostrando todos los campos en modo disabled -->
        <!-- Establezco el método 'POST' y el archivo del controlador en el action -->
        <form method="POST" action="../controlador/UsuariosController.php">
            <!-- Indico la accion eliminarUsuario para diferenciar los input de este formulario del resto de formularios en el controlador -->
            <input type="hidden" name="accion" value="eliminarUsuario">
            <!-- Añado el id del usuario que se va a eliminar como input invisible en el formulario para que se envíe al controlador -->
            <input type="hidden" class="form-control" id="idUsuario" name="idUsuario" value="<?=$id;?>">


            <!-- Añado todos los datos del usuario con los input en disabled para que no se envíen al controlador ni el usuario pueda modificarlos, solo para que sean visibles -->
            
            <div class="mb-3">
                <label for="nombre" class="form-label">ID del usuario:</label>
                <input type="text" class="form-control" id="idUsuario" name="idUsuario" value="<?=$usuario['idUsuario'];?>" disabled>
            </div>

            <div class="mb-3">
                <label for="nombre" class="form-label">Nombre:</label>
                <input type="text" class="form-control" id="nombre" name="nombre" value="<?=$usuario['nombre'];?>" disabled>
            </div>
            <div class="mb-3">
                <label for="apellidos" class="form-label">Apellidos:</label>
                <input type="text" class="form-control" id="apellidos" name="apellidos" value="<?=$usuario['apellidos'];?>" disabled>
            </div>
            <div class="mb-3">
                <label for="edad" class="form-label">Edad:</label>
                <input type="number" class="form-control" id="edad" name="edad" value="<?=$usuario['edad'];?>" disabled>
            </div>
            <div class="mb-3">
                <label for="email" class="form-label">Email:</label>
                <input type="email" class="form-control" id="email" name="email" value="<?=$usuario['email'];?>" disabled>
            </div>

            <div class="mb-3">
                <label for="email" class="form-label">Fecha de suscripción:</label>
                <input type="text" class="form-control" id="fechaSuscripcion" name="fechaSuscripcion" value="<?=$usuario['fechaSuscripcion'];?>" disabled>
            </div>

            <div class="mb-3">
                <label for="email" class="form-label">Duración de la suscripción:</label>
                <input type="text" class="form-control" id="duracionSuscripcion" name="duracionSuscripcion" value="<?=$usuario['duracionSuscripcion'];?>" disabled>
            </div>

            <!-- Muestro el botón para volver a la lista de usuarios junto al botón de submit para eliminar el usuario, ambos en el medio debajo -->
            <div class="d-flex justify-content-center mt-4">
                <!-- Botón que redirige a la lista principal de usuarios -->
                <a class="btn btn-secondary m-2" style="color: white;" href="../vista/listaUsuarios.php">Volver a la lista</a>
                <button type="submit" class="btn btn-danger m-2">Eliminar usuario</button>
                <!-- (El botón tipo submit de eliminar usuario enviará al controlador únicamente la accion eliminarUsuario y el id del usuario (los input ocultos del inicio del formulario)) -->
                <!-- (Los campos del formulario que están en disabled no se enviarán al controlador) -->
            </div>
        </form>
        <br>
    </div>
</body>
</html>