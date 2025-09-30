<?php
session_start();
// Incluyo el archivo UsuariosController.php para poder acceder a sus métodos
require_once '../controlador/UsuariosController.php';

// Compruebo que el usuario esté registrado, si no lo está, le redirijo a la página principal
// (Esto controla un posible error, evitando que un usuario acceda sin registrarse)
if (!isset($_SESSION['idUsuario'])) {
    header("Location: ../vista/index.php");
    exit();
}

// Obtengo el id del usuario de la sesión
$id = $_SESSION['idUsuario'];

// Instancio un objeto de la clase UsuariosController para poder acceder a sus métodos
$controller = new UsuariosController();
// Llamo al método obtenerTareasUsuario() para obtener todas las tareas del usuario mediante su id
$tareas = $controller->obtenerTareasUsuario($id);
?>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista de tareas</title>
    <!-- Enlace a bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <!-- Enlazo el archivo css con el estilo -->
    <link rel="stylesheet" href="../css/estilo.css">
</head>
<body style="background-color:rgb(233, 245, 255);">
    <div class="container mt-5">
        <h2 class="mb-4 text-center" style="color:rgb(73, 106, 155);">LISTA DE TAREAS</h2>
        <div class="table-responsive shadow-lg p-3 mb-5 bg-white rounded">
            <table class="table table-bordered table-sm small text-center">
                <thead style="background-color:rgb(179, 217, 255); color:rgb(44, 111, 187);">
                    <tr>
                        <th>Nombre</th>
                        <th>Descripción</th>
                        <th>Estado</th>
                        <th>Fecha de creación</th>
                        <th>(Fecha límite)</th>
                        <th class="col-2">Gestionar Tareas</th>
                    </tr>
                </thead>
                <tbody>
                    <!-- Muestro los datos de cada tarea una a una (fila a fila) -->
                    <?php foreach ($tareas as $tarea): ?>
                        <tr class="align-middle">
                            <td><?= $tarea['nombreTarea'] ?></td>
                            <td style="max-width: 250px; word-wrap: break-word;"><?= $tarea['descripcion'] ?></td>
                            <td><?= $tarea['estado'] ?></td>
                            <td><?= $tarea['fechaCreacion'] ?></td>
                            <td><?= $tarea['fechaLimite'] ?? '-' ?></td>
                            <td>
                                <!-- Botones para gestionar las tareas -->
                                <div class="d-flex gap-1 mb-1 justify-content-center">
                                    <div class="d-flex flex-fill">
                                        <!-- Botón que marca la tarea como completada -->
                                        <form action="../controlador/UsuariosController.php" method="POST" class="d-inline">
                                            <input type="hidden" name="accion" value="completarTarea">
                                            <input type="hidden" name="idTarea" value="<?= $tarea['idTarea'] ?>">
                                            <button type="submit" class="btn btn-outline-success btn-sm flex-fill">Marcar como completada ✓</button>
                                        </form>
                                    </div>
                                    <div class="d-flex flex-fill">
                                        <!-- Botón que elimina la tarea -->
                                        <form action="../controlador/UsuariosController.php" method="POST" class="d-inline">
                                            <input type="hidden" name="accion" value="eliminarTarea">
                                            <input type="hidden" name="idTarea" value="<?= $tarea['idTarea'] ?>">
                                            <button type="submit" class="btn btn-outline-danger btn-sm flex-fill">Eliminar tarea ✕</button>
                                        </form>
                                    </div>
                                </div>
                                <!-- Botón que redirige a la página para editar los datos de la tarea -->
                                <div class="d-flex justify-content-center flex-nowrap">
                                    <div class="col-12">
                                        <a href="editarTarea.php?id=<?= $tarea['idTarea'] ?>" class="btn btn-outline-secondary btn-sm w-100">Editar los datos de la tarea</a>
                                    </div>
                                </div>
                            </td>                        
                        </tr>
                    <?php endforeach; ?>
                </tbody>
            </table>
        </div>
        <!-- Botones para añadir una nueva tarea o cerrar la sesión y volver al inicio -->
        <div class="d-flex justify-content-center gap-3 mt-3">
            <a href="../vista/agregarTarea.php" class="btn btn-primary btn-lg">Añadir nueva tarea</a>
            <a href="../controlador/cerrarSesion.php" class="btn btn-secondary btn-lg">Cerrar sesión</a>
        </div>
    </div>
    <!-- Enlace a bootstrap -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN6jIeHz" crossorigin="anonymous"></script>
</body>
</html>