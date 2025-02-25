<?php 
require_once 'controlador/RecetasController.php';

// Instancio un objeto de la clase RecetasController para poder llamar a sus métodos
$controller = new RecetasController();
// Obtengo el nombre de la receta buscada si se se ha buscado una receta
$recetaBuscada = $_GET['receta'] ?? '';
// Inicializo un array vacío para almacenar las recetas que se van a mostrar
$recetas = [];

// Si se ha buscado una receta, obtengo las recetas que coincidan con el nombre de la receta buscada
if (!empty($recetaBuscada)) {
    $recetas = $controller ->obtenerRecetas($recetaBuscada);
}
?> 

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Blog de Recetas Iris Pérez</title>
    <!-- Enlazo el archivo CSS de Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Enlazo la hoja de estilos (archivo css con todo el estilo de la página) -->
    <link rel="stylesheet" href="estilo.css">
</head>
<body>  
    <div class="container">
        <h1 class="mt-5 mb-4">👨🏻‍🍳 Busca una receta de cocina 👩🏻‍🍳</h1>
        <nav class="navbar shadow-sm">
            <div class="container-fluid row align-items-center">
                <div class="col-md-2 text-md-end fw-bold">Nombre de la receta:</div>
                <div class="col-md-10">
                    <form class="d-flex" role="search" action="controlador/RecetasController.php" method="POST">
                        <!-- Envío un input oculto con la acción del formulario para diferenciarlo en el controlador -->
                        <input type="hidden" name="accion" value="buscarReceta">
                        <!-- Envío al controlador el nombre de la receta buscada -->
                        <input class="form-control me-3" type="search" name="recetaBuscada" id="recetaBuscada" placeholder="Escribe aquí el nombre de la receta que quieras buscar" aria-label="Search">
                        <button class="btn btn-success" type="submit">Buscar</button>
                    </form>
                </div>
            </div>
        </nav>
        <!-- Si se ha buscado una receta, muestro la tabla con las recetas que coincidan con el nombre -->
        <?php if (!empty($recetas)): ?>
        <div class="table-responsive mt-4">
            <table class="table table-bordered table-sm text-center">
                <thead>
                    <tr>
                        <th class="col-2">Receta</th>
                        <th class="col-4">Ingredientes</th>
                        <th class="col-5">Preparación</th>
                        <th class="col-1">Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <!-- Para cada receta, muestro sus datos (nombre, ingredientes, preparación) y sus acciones (editar y eliminar) -->
                    <?php foreach ($recetas as $receta): ?>
                        <tr class="align-middle">
                            <td><?= $receta['nombre'] ?></td>
                            <!-- Uso la función nl2br para convertir los saltos de línea de \n a <br> para que se muestren bien los datos en el HTML -->
                            <td class="text-start px-4 py-3"><?= nl2br($receta['ingredientes']) ?></td>
                            <td class="text-start px-4 py-3"><?= nl2br($receta['preparacion']) ?></td>
                            <td>
                                <div class="justify-content-center">
                                    <div class="d-flex m-3">
                                        <form action="../vlogRecetasIrisPerez/controlador/RecetasController.php" method="POST">
                                            <!-- Envío un input oculto con la acción del formulario para diferenciarlo en el controlador -->
                                            <input type="hidden" name="accion" value="eliminarReceta">
                                            <!-- Envío un input oculto con el nombre de la receta buscada para redirigir al usuario de vuelta a donde estaba al terminar el proceso en el controlador (así verá que se ha eliminado la receta) -->
                                            <input type="hidden" name="recetaBuscada" value="<?= $recetaBuscada ?>">
                                            <!-- Envío al controlador el id de la receta a eliminar -->
                                            <input type="hidden" name="idReceta" value="<?= $receta['idReceta'] ?>">
                                            <button type="submit" class="btn btn-danger btn-sm">Eliminar</button>
                                        </form>
                                    </div>
                                    <div class="d-flex m-3">
                                        <!-- Envío al usuario a la página de edición de la receta pasando (por la url) el id de la receta a editar y el nombre de la receta buscada -->
                                        <a href="vista/editarReceta.php?id=<?= urlencode($receta['idReceta'])?>&receta=<?= urlencode($recetaBuscada) ?>" class="btn btn-secondary btn-sm">Editar</a>
                                    </div>
                                </div>
                            </td>                        
                        </tr>
                    <?php endforeach;?>
                </tbody>
            </table>
        </div>
        <?php endif; ?>
    </div>
    <!-- Enlazo el archivo JS de Bootstrap -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>