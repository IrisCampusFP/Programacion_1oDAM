<?php
// Incluyo el archivo UsuariosController.php de la carpeta controlador para poder utilizar sus métodos
require_once '../controlador/UsuariosController.php';

// Obtengo el id del usuario que va a actualizar sus paquetes recibido mediante la URL con el método 'GET'
$id = $_GET['id'];

// Instancio un objeto de la clase UsuariosController()
$controller = new UsuariosController();
// Llamo al método obtenerPaquetesUsuario que obtiene los ids de los paquetes contratados por el usuario con id = $id (id del usuario en el que se ha pulsado el botón 'Actualizar paquetes' obtenido de la URL)
// (Obtengo los paquetes contratados para mostrar al usuario los paquetes que tiene ya seleccionados)
$paquetesContratados = $controller->obtenerPaquetesUsuario($id);

// Llamo al método obtenerUsuarioPorId que obtiene todos los datos del usuario con ese id y los guardo en la variable $usuario
// (Utilizaré los datos del usuario para establecer las restricciones en función de su edad y su plan)
$usuario = $controller->obtenerUsuarioPorId($id);
?>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Actualizar Paquetes (StreamWeb)</title>
    <!-- Enlace a bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <style>
        .masAbajo{
            margin-top: 12%;
        }
    </style>
</head>

<body class="text-center mt-5">
    <h1 class="mb-4 masAbajo">Selecciona uno o varios paquetes</h1>
    <!-- Muestro toda la información importante relacionada con las restricciones a la hora de seleccionar paquetes adicionales -->
    <p>(Los usuarios del plan básico solo podrán seleccionar un paquete y los menores de 18 años solo podrán contratar el pack infantil)</p>
    <!-- Creo un formulario con el método 'POST' y enlazo el controlador en el action para enviar allí los datos recibidos -->
    <form method="POST" action="../controlador/UsuariosController.php">
    <!-- Añado un input oculto con la accion actualizarPaquetes para diferenciar este formulario del resto de formularios en el controlador -->
    <input type="hidden" name="accion" value="actualizarPaquetes">

    <!-- Añado otro input oculto que envía al controlador el id del usuario en el que se actualizan paquetes -->
    <input type="hidden" class="form-control" id="idUsuario" name="idUsuario" value="<?=$id;?>" required>

        <!-- Organizo los paquetes a seleccionar en columnas de Bootstrap y los centro -->
        <div class="border rounded p-3 col-3 d-inline-block text-center">
            <div>
                <!-- Establezco el tipo de input: radio para los usuarios con plan básico (solo pueden seleccionar un paquete), checkbox para el resto de usuarios (los usuarios pueden seleccionar más de un paquete, pueden contratar todos los paquetes que deseeen) -->
                <input type="<?php if ($usuario['idPlan'] == 1){echo "radio";} else {echo "checkbox";}?>" class="btn-check" name="idPaquetes[]" value="1" id="paqueteDeporte" autocomplete="off" 
                <?php if (in_array(1, $paquetesContratados)){echo 'checked';}   // Si el usuario ya tiene este paquete contratado, este aparecerá seleccionado ?>
                <?php if ($usuario['edad'] < 18 or $usuario['duracionSuscripcion'] != 'Anual'){echo 'disabled';}   // Si el usuario es menor de 18 años o la duración de su suscripción es de menos de 1 año, no podrá seleccionar este paquete (aparecerá como 'disabled') ?>>
                <label class="btn btn-outline-primary w-100" for="paqueteDeporte">
                    <strong>Deporte</strong>
                </label>
            </div>
            <div>
                <p class="mt-2"><strong>6'99€ / mes</strong> 
                    <br>(Solo disponible para suscripciones de 1 año)
                </p>
            </div>
        </div>

        <div class="border rounded p-3 col-3 d-inline-block text-center">
            <div>
                <!-- (Establezco de nuevo el input (radio o checkbox) como en el div anterior, dependiendo de si el usuario tiene el plan básico u otro plan) -->
                <input type="<?php if ($usuario['idPlan'] == 1){echo "radio";} else {echo "checkbox";}?>" class="btn-check" name="idPaquetes[]" value="2" id="paqueteCine" autocomplete="off" 
                <?php if (in_array(2, $paquetesContratados)) echo 'checked';   // Si el usuario tiene este paquete contratado aparecerá ya seleccionado ('checked') ?>
                <?php if ($usuario['edad'] < 18) echo 'disabled';   // Si el usuario es menor de edad no podrá seleccionar este paquete (aparecerá como disabled) ?>>
                <label class="btn btn-outline-primary w-100" for="paqueteCine">
                    <strong>Cine</strong>
                </label>
            </div>
            <div>
                <p class="mt-2"><strong>7'99€ / mes</strong>
                    <br>🍿🙄
                </p>
            </div>
        </div>

        <div class="border rounded p-3 col-3 d-inline-block text-center">
            <div>
                <!-- Establezco el input igual que en los divs anteriores (como en los otros paquetes) -->
                <input type="<?php if ($usuario['idPlan'] == 1){echo "radio";} else {echo "checkbox";}?>" class="btn-check" name="idPaquetes[]" value="3" id="paqueteInfantil" autocomplete="off" 
                <?php if (in_array(3, $paquetesContratados)) echo 'checked';   // Si el usuario ya tiene este paquete contratado aparecerá seleccionado ('checked') ?>>
                <label class="btn btn-outline-primary w-100" for="paqueteInfantil">
                    <strong>Infantil</strong>
                </label>
            </div>
            <div>
                <p class="mt-2"><strong>4'99€ / mes</strong>
                    <br class="mt-1">(Para menores de 18 años)
                </p>
            </div>
        </div>
        <div class="mt-4">
            <!-- Debajo de los paquetes, en grande, están los botones para volver a la lista de usuarios y para confirmar la selección y actualizar los paquetes -->
            <a href="listaUsuarios.php" class="btn btn-lg btn-secondary m-2">Volver a la lista</a>
            <button type="submit" class="btn btn-lg btn-success m-2">Actualizar paquetes</button>
        </div>
    </form>
    <!-- Enlace a bootstrap -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>

</html>