<?php
session_start();
?>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>StreamWeb -> Iniciar Sesión</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <!-- Enlazo el archivo css con los estilos comunes -->
    <link rel="stylesheet" href="../css/estilo.css">
    <style>
        /* Estilo específico para el formulario de inicio de sesión */
        .container {
            background-color: white;
            border-radius: 10px;
            box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
            padding: 60px;
            margin-top: 5%;
            max-width: 600px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1 class="text-center mb-3">Iniciar Sesión</h1>
        <h3 class="text-center mb-4 fs-3">Bienvenid@ de nuevo 😃</h3>
        <form method="POST" action="../controlador/UsuariosController.php">
            <!-- Añado un input oculto para indicar la acción en el controlador -->
            <input type="hidden" name="accion" value="iniciarSesion">

            <!-- Campos del formulario para el inicio de sesión de un usuario ya registrado -->
            <div class="mb-3">
                <label for="correo" class="form-label">Correo electrónico:</label>
                <input type="email" class="form-control" id="correo" name="correo" required>
            </div>
            <div class="mb-3">
                <label for="clave" class="form-label">Contraseña:</label>
                <input type="password" class="form-control" id="clave" name="clave" required>
            </div>

            <!-- Botones para enviar el formulario de inicio de sesión o volver al inicio -->
            <div class="text-center mt-4 d-flex justify-content-center gap-3">
                <a href="../index.html" class="btn btn-secondary">Volver al inicio</a>
                <button type="submit" class="btn btn-primary">Iniciar Sesión</button>
            </div>
        </form>
    </div>

    <!-- Muestro un mensaje de error en caso de que el inicio de sesión falle -->
    <?php if (isset($_GET['mensaje'])) { ?>
    <div class="alert mensaje text-center mt-4 mx-auto fw-bold" style="max-width: 550px;">
        <?php echo htmlspecialchars($_GET['mensaje']); } ?>
    </div>
</body>
</html>