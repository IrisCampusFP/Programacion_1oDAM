<?php
// Incluyo el archivo UsuariosController.php de la carpeta controlador
require_once '../controlador/UsuariosController.php';
// Instancio un objeto de la clase UsuariosController
$controller = new UsuariosController();
// Llamo al método listarUsuarios() que obtiene todos los datos de todos los usuarios y los introduzco en la variable $usuarios
$usuarios = $controller->listarUsuarios();
?>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Listado Usuarios (StreamWeb)</title>
    <!-- Enlace a bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body>
    <div class="m-3">
        <h3 class="mb-3">Lista de usuarios registrados:</h1>
        <!-- Creo la tabla en la que se van a mostrar los datos de todos los usuarios junto con los botones para modificar los datos, eliminar usuarios, cambiar planes y añadir paquetes adicionales -->
        <table class="table table-responsive table-sm small text-center">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Apellidos</th>
                    <th>Edad</th>
                    <th>Email</th>
                    <th>Plan</th>
                    <th>Duración</th>
                    <th>Fecha Suscripción</th>
                    <th>Paquetes Adicionales</th>
                    <th>Costo Total Mensual</th>
                    <!-- Hago la columna de los botones más ancha -->
                    <th class="col-2">Gestionar Usuario</th>
                </tr>
            </thead>
            <tbody>
                <!-- Para cada usuario, muestro sus datos guardados en la base de datos en una fila de la tabla campo por campo obteniendo los datos de los campos que quiero mostrar -->
                <?php foreach ($usuarios as $usuario): ?>
                    <tr class="align-middle">
                        <td><?= $usuario['idUsuario']?></td>
                        <td><?= $usuario['nombre'] ?></td>
                        <td><?= $usuario['apellidos'] ?></td>
                        <td><?= $usuario['edad'] ?></td>
                        <td><?= $usuario['email'] ?></td>
                        <td><?= $usuario['plan'] ?></td>
                        <td><?= $usuario['duracionSuscripcion'] ?></td>
                        <td><?= $usuario['fechaSuscripcion'] ?></td>
                        <td><?= $usuario['paquetesAdicionales'] ?></td>
                        <td><?= $usuario['costoTotal'] . "€" ?></td>
                        <td>
                            <!-- Coloco los botones con bootstrap usando col -->
                            <div class="d-flex gap-1 mb-1 justify-content-center">
                                <div class="col-6">
                                    <!-- Botón que redirige al archivo actualizarUsuario.php enviando el id del usuario en el que se pulsa este botón en la URL -->
                                    <a href="actualizarUsuario.php?id=<?= $usuario['idUsuario'] ?>" class="btn btn-sm btn-outline-secondary w-100">Editar datos</a>
                                </div>
                                <div class="col-6">
                                    <!-- Botón que redirige al archivo eliminarUsuario.php enviando el id del usuario en el que se pulsa este botón en la URL -->
                                    <a href="eliminarUsuario.php?id=<?= $usuario['idUsuario'] ?>" class="btn btn-sm btn-outline-danger w-100">Eliminar usuario</a>
                                </div>
                            </div>
                            <div class="d-flex gap-1 justify-content-center flex-nowrap">
                                <div class="col-6">
                                    <!-- Botón que redirige al archivo actualizarPlan.php enviando el id del usuario en el que se pulsa este botón en la URL -->
                                    <a href="actualizarPlan.php?id=<?= $usuario['idUsuario'] ?>" class="btn btn-sm btn-outline-primary w-100">Cambiar plan</a>
                                </div>
                                <div class="col-6">
                                    <!-- Botón que redirige al archivo actualizarPaquetes.php enviando el id del usuario en el que se pulsa este botón en la URL -->
                                    <a href="actualizarPaquetes.php?id=<?= $usuario['idUsuario'] ?>" class="btn btn-sm btn-outline-success w-100">Paquetes Adicionales</a>
                                </div>
                            </div>
                        </td>                        
                    </tr>
                <?php endforeach; ?>
            </tbody>
        </table>
        <!-- Al final de la tabla, muestro un botón para agregar un nuevo usuario (que redirige al archivo registrarUsuario.php) y otro botón para volver al inicio de la página (que redirige al index.html) -->
        <a href="../vista/registrarUsuario.html" class="btn btn-primary">Agregar un nuevo usuario</a>
        <a href="../index.html" class="btn btn-secondary m-2">Volver al inicio</a>
    </div>
    <!-- Enlace a bootstrap -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN6jIeHz" crossorigin="anonymous"></script>
</body>
</html>
