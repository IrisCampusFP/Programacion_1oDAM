<?php
session_start();
?>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>StreamWeb -> Registrarse</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <!-- Enlazo el archivo css con los estilos comunes con el resto de páginas de la web -->
        <link rel="stylesheet" href="../css/estilo.css">
    <style>
        /* Estilo específico para el formulario de registro de usuarios */
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
        <h1 class="text-center mb-4 py-2">¡Regístrate y gestiona tus tareas de forma sencilla! 😊</h1>
        <form method="POST" action="../controlador/UsuariosController.php">
            <!-- Añado un input oculto para indicar la acción en el controlador -->
            <input type="hidden" name="accion" value="registrar">

            <!-- Campos del formulario para el registro de un nuevo usuario -->
            <div class="mb-3">
                <label for="nombreUsuario" class="form-label">Nombre de usuario:</label>
                <input type="text" class="form-control" id="nombreUsuario" name="nombreUsuario" required>
            </div>
            <div class="mb-3">
                <label for="correo" class="form-label">Correo electrónico:</label>
                <input type="email" class="form-control" id="correo" name="correo" required>
            </div>
            <div class="mb-3">
                <label for="clave" class="form-label">Contraseña:</label>
                <input type="password" class="form-control" id="clave" name="clave" required>
            </div>

            <!-- Botones para enviar el formulario de registro o volver al inicio -->
            <div class="d-flex justify-content-center mt-4">
                <a href="../index.html" class="btn btn-secondary me-1" style="min-width: 150px;">Volver al inicio</a>   
                <button type="submit" class="btn btn-primary ms-1" style="min-width: 150px;">Registrarme</button>     
            </div>
        </form>
        <br>
    </div>
</body>
</html>