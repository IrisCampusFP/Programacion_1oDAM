<?php
session_start();
// Incluyo el archivo UsuariosController.php de la carpeta controlador para poder acceder a sus métodos
require_once '../controlador/UsuariosController.php';

// Compruebo que el usuario esté registrado, si no lo está, le redirijo a la página principal
// (Esto controla un posible error, evitando que un usuario acceda sin registrarse)
if (!isset($_SESSION['idUsuario'])) {
    header("Location: ../vista/index.php");
    exit();
}

// Obtengo el id de la tarea mediante la URL con el método 'GET'
$id = htmlspecialchars($_GET['id']);

// Instancio un objeto de la clase UsuariosController()
$controller = new UsuariosController();
// Llamo al método obtenerTareaPorId() para obtener la tarea a editar
$tarea = $controller->obtenerTareaPorId($id);
?>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Editar tarea</title>
    <!-- Enlace a Bootstrap -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <!-- Enlazo el archivo css con el estilo común con el resto de páginas de la web -->
    <link rel="stylesheet" href="../css/estilo.css">
    <style>
        /* Estilo específico para el formulario de edición de tareas */
        .container {
            background-color: white;
            border-radius: 10px;
            box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
            padding: 30px;
        }
    </style>
</head>
<body>
    <div class="container mt-5">
        <h1 class="text-center mb-0">Editar tarea</h1>
        
        <form method="POST" action="../controlador/UsuariosController.php">
            <!-- Añado un input invisible para indicar la acción en el controlador -->
            <input type="hidden" name="accion" value="editarTarea">
            <!-- Envío el id de la tarea a editar al controlador -->
            <input type="hidden" name="idTarea" value="<?=$id?>">


            <!-- Muestro los datos de la tarea a editar en los campos del formulario estableciendo automáticamente el value con los datos registrados -->
            <div class="mb-3">
                <label for="nombreTarea" class="form-label">Nombre de la tarea:</label>
                <input type="text" class="form-control" id="nombreTarea" name="nombreTarea" value="<?= $tarea['nombreTarea'] ?>" required>
            </div>
            <div class="mb-3">
                <label for="descripcion" class="form-label">Descripción:</label>
                <textarea type="text" class="form-control" id="descripcion" name="descripcion" rows="4" required><?= $tarea['descripcion'] ?></textarea>
            </div>
            <div class="mb-3">
                <label for="fechaLimite" class="form-label">Fecha Límite (opcional):</label>
                <input type="datetime-local" class="form-control" id="fechaLimite" name="fechaLimite" min="<?= date('Y-m-d\TH:i'); ?>" value="<?= date('Y-m-d\TH:i', strtotime($tarea['fechaLimite'])); ?>">
                <!-- Adapto la fecha registrada a un formato compatible con el formulario -->
            </div>

            <!-- Permito al usuario modificar el estado de la tarea por si, por ejemplo, quisiera desmarcar una tarea completada -->
            <div class="mb-3">
                <label for="clave" class="form-label">Modificar estado:</label>
                <select class="form-select" id="estado" name="estado" required>
                    <option value="Completada" <?php if ($tarea['estado'] == 'Completada') {echo 'selected';} ?>>Completada</option>
                    <option value="Pendiente" <?php if ($tarea['estado'] == 'Pendiente') {echo 'selected';} ?>>Pendiente</option>
                </select>
            </div>

            <div class="d-flex justify-content-center mt-3">
                <a href="../vista/listaTareas.php" class="btn btn-secondary m-2" style="color: white;">Volver a la lista</a>
                <button type="submit" class="btn btn-primary m-2">Actualizar tarea</button>
            </div>
        </form>
    </div>
</body>
</html>