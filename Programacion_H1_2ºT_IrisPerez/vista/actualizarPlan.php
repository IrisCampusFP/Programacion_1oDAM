<?php
// Incluyo el archivo UsuariosController.php de la carpeta controlador para poder acceder a sus métodos
require_once '../controlador/UsuariosController.php';
// Obtengo el id del usuario recibido a través de la URL con el método 'GET'
$id = $_GET['id'];

// Instancio un objeto de la clase UsuariosController()
$controller = new UsuariosController();
// Llamo al método obtenerUsuarioPorId() para obtener todos los datos del usuario, así obtendré el plan al cual está suscrito para mostrarlo ya seleccionado
$usuario = $controller->obtenerUsuarioPorId($id);
?>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cambiar Plan (StreamWeb)</title>
    <!-- Enlace a Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <style>
        .masAbajo {
            margin-top: 12%;
        }
    </style>
</head>

<body class="text-center mt-5">
    <h1 class="mb-4 masAbajo">Elige un nuevo plan</h1>
    <!-- Establezco el método 'POST' y enlazo el controlador en el action para enviar allí los datos recibidos del formulario -->
    <form method="POST" action="../controlador/UsuariosController.php">
        <!-- Añado un input oculto con nombre accion y valor actualizarPlan para diferenciar este formulario del resto en el controlador -->
        <input type="hidden" name="accion" value="actualizarPlan">

        <!-- Añado otro input oculto para enviar al controlador el id del usuario en el que se va a actulizar el plan -->
        <input type="hidden" class="form-control" id="idUsuario" name="idUsuario" value="<?=$id;?>" required>


        <!-- (A continuación, enviaré al controlador el id del plan que seleccione el usuario) -->

        <!-- Organizo los planes en columnas de bootstrap y los centro -->
        <div class="border rounded p-3 col-3 d-inline-block text-center">
            <div>
                <!-- Establezco el input en tipo radio para que el usuario solo pueda seleccionar un plan -->
                <input type="radio" class="btn-check" name="idPlan" value="1" id="planBasico" autocomplete="off" <?php if ($usuario['idPlan'] == 1) echo 'checked' // Si el usuario ya tiene contratado este plan, aparecerá seleccionado ?>>
                <!-- Enviará al controlador: $_POST['idPlan'] = 1 -->
                <label class="btn btn-outline-primary w-100" for="planBasico">
                    <strong>Básico</strong>
                </label>
            </div>
            <div>
                <p class="mt-2"><strong>9'99€ / mes</strong>
                    <br>(1 dispositivo)
                </p>
            </div>
        </div>

        <div class="border rounded p-3 col-3 d-inline-block text-center m-3">
            <div>
                <input type="radio" class="btn-check" name="idPlan" value="2" id="planEstandar" autocomplete="off" <?php if ($usuario['idPlan'] == 2) echo 'checked' // Si el usuario ya tiene contratado este plan, aparecerá seleccionado ?>>
                <!-- Enviará al controlador: $_POST['idPlan'] = 2 -->
                <label class="btn btn-outline-primary w-100" for="planEstandar">
                    <strong>Estándar</strong>
                </label>
            </div>
            <div>
                <p class="mt-2"><strong>13'99€ / mes</strong>
                    <br>(2 dispositivos)
                </p>
            </div>
        </div>

        <div class="border rounded p-3 col-3 d-inline-block text-center">
            <div>
                <input type="radio" class="btn-check" name="idPlan" value="3" id="planPremium" autocomplete="off" <?php if ($usuario['idPlan'] == 3) echo 'checked' // Si el usuario ya tiene contratado este plan, aparecerá seleccionado ?>>
                <!-- Enviará al controlador: $_POST['idPlan'] = 3 -->
                <label class="btn btn-outline-primary w-100" for="planPremium">
                    <strong>Premium</strong>
                </label>
            </div>
            <div>
                <p class="mt-2"><strong>17'99€ / mes</strong>
                    <br class="mt-1">(4 dispositivos)
                </p>
            </div>
        </div>
        <!-- Debajo, muestro en grande un botón para volver a la lista de usuarios junto al botón 'Actualizar plan' que envía los datos recibidos del formulario al controlador -->
        <div class="mt-4">
            <a href="listaUsuarios.php" class="btn btn-lg btn-secondary m-2">Volver a la lista</a>
            <button type="submit" class="btn btn-lg btn-success m-2">Actualizar plan</button>
        </div>
    </form>
    <!-- Enlace a Bootstrap -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>

</html>