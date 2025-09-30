<?php
// Incluyo el archivo UsuariosController.php de la carpeta controlador para poder acceder a sus métodos
require_once '../controlador/UsuariosController.php';

// Obtengo el id del usuario de la URL con el método 'GET'
$id = $_GET['id'];

// Instancio un objeto de la clase UsuariosController()
$controller = new UsuariosController();
// Llamo al método obtenerUsuarioPorId() con el id del usuario obtenido de la URL y guardo los datos del usuario en la variable $usuario
$usuario = $controller->obtenerUsuarioPorId($id);
?>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Actualizar Usuario (StreamWeb)</title>
    <!-- Enlace a Bootstrap -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <h1 class="text-center">Actualiza los datos del usuario</h1>
        <!-- Muestro el formulario con los datos del usuario en el que se ha pulsado el botón 'Editar datos' ya escritos, listos para ser modificados -->
        <form method="POST" action="../controlador/UsuariosController.php">
            <!-- Añado un input oculto con la accion actualizarUsuario para diferenciar este formulario del resto de formularios en el controlador -->
            <input type="hidden" name="accion" value="actualizarUsuario">
            <!-- Añado otro input oculto con el id del usuario en el que se ha pulsado el botón 'Editar datos' para indicar el usuario a modificar en el controlador -->
            <input type="hidden" class="form-control" id="idUsuario" name="idUsuario" value="<?=$id;?>" required>
            
            
            <!-- Relleno automáticamente cada campo del formulario escribiendo en value="" los datos del usuario recibidos del controlador -->
            
            <div class="mb-3">
                <label for="nombre" class="form-label">Nombre:</label>
                <input type="text" class="form-control" id="nombre" name="nombre" value="<?=$usuario['nombre'];?>" required>
            </div>
            <div class="mb-3">
                <label for="apellidos" class="form-label">Apellidos:</label>
                <input type="text" class="form-control" id="apellidos" name="apellidos" value="<?=$usuario['apellidos'];?>" required>
            </div>
            <div class="mb-3">
                <label for="edad" class="form-label">Edad:</label>
                <input type="number" class="form-control" id="edad" name="edad" value="<?=$usuario['edad'];?>" required>
            </div>
            <div class="mb-3">
                <label for="email" class="form-label">Email:</label>
                <input type="email" class="form-control" id="email" name="email" value="<?=$usuario['email'];?>" required>
            </div>
            <div class="mb-3">
                <label for="clave" class="form-label">Clave:</label>
                <input type="password" class="form-control" id="clave" name="clave" value="<?=$usuario['clave'];?>" required>
            </div>

            <div class="mb-3">
                <label for="fechaSuscripcion" class="form-label">Fecha de suscripción:</label>
                <!-- (El input type="datetime-local" permitirá seleccionar fecha y hora por si hubiera que hacer alguna modificación en la fecha de suscripción) -->
                <input type="datetime-local" class="form-control" id="fechaSuscripcion" name="fechaSuscripcion" value="<?= date('Y-m-d\TH:i', strtotime($usuario['fechaSuscripcion'])); ?>" required>
                <!-- (Convierto el dato de la fecha y hora recibido de la base de datos como string para que se muestre correctamente en el formulario) -->
            </div>

            <div class="mb-3">
                <label for="clave" class="form-label">Duración de la suscripción:</label>
                <!-- Muestro como seleccionada ('selected') la duracion de la suscripcion que tenga registrada usuario -->
                <!-- Permito al usuario cambiar de opción, limitando con la etiqueta select la selección a 'Mensual' o 'Anual' -->
                <select class="form-select" id="duracionSuscripcion" name="duracionSuscripcion" required>
                    <option value="Mensual" <?php if ($usuario['duracionSuscripcion'] == 'Mensual'){echo 'selected';}?>>Mensual</option>
                    <option value="Anual" <?php if ($usuario['duracionSuscripcion'] == 'Anual'){echo 'selected';} ?>>Anual</option>
                </select>
            </div>

            <!-- Al final del formulario, muestro el botón para volver a la lista de usuarios junto al botón de 'Actualizar usuario' de tipo submit para enviar el formulario -->
            <div class="d-flex justify-content-center mt-3">
                <a href="../vista/listaUsuarios.php" class="btn btn-secondary m-2" style="color: white;">Volver a la lista</a>
                <button type="submit" class="btn btn-primary m-2">Actualizar usuario</button>
            </div>
        </form>
        <br>
    </div>
</body>
</html>