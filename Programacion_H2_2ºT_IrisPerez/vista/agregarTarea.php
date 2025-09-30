<?php
// Inicio la sesión para poder acceder a las variables de sesión
session_start();

// Compruebo que el usuario esté registrado, si no lo está, le redirijo a la página principal
// (Esto controla un posible error, evitando que un usuario acceda sin registrarse)
if (!isset($_SESSION['idUsuario'])) {
    header("Location: ../vista/index.php");
    exit();
}
?>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Creación nueva tarea</title>
    <!-- Enlace al JS de Bootstrap -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <!-- Enlazo el archivo css con los estilos comunes -->
    <link rel="stylesheet" href="../css/estilo.css">
    <style>
        /* Estilo específico para el formulario de creación de tareas */
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
        <h1 class="text-center mb-3">Introduce los datos de la nueva tarea</h1>
        <!-- Formulario para agregar una tarea nueva -->
        <form method="POST" action="../controlador/UsuariosController.php">
            <!-- Input invisible para indicar la accion en el controlador -->
            <input type="hidden" name="accion" value="agregarTarea">

            <div class="mb-3">
                <label for="nombreTarea" class="form-label">Nombre de la tarea:</label>
                <input type="text" class="form-control" id="nombreTarea" name="nombreTarea" required>
            </div>
            <div class="mb-3">
                <label for="descripcion" class="form-label">Descripción:</label>
                <textarea type="text" class="form-control" id="descripcion" name="descripcion" rows="4" required></textarea>
            </div>
            <!-- La fecha límite es opcional (se puede dejar vacía). Establezco el mínimo de la fecha límite en la fecha actual, para que no sea incoherente -->
            <div class="mb-3">
                <label for="fechaLimite" class="form-label">Fecha Límite (opcional):</label>
                <input type="datetime-local" class="form-control" id="fechaLimite" name="fechaLimite" min="<?= date('Y-m-d\TH:i'); ?>">
            </div>
            
            <div class="d-flex justify-content-center mt-3">
                <a href="../vista/listaTareas.php" class="btn btn-secondary m-2" style="color: white;">Volver a la lista</a>
                <button type="submit" class="btn btn-primary m-2">Agregar tarea</button>
            </div>
        </form>
        <br>
    </div>
</body>
</html>