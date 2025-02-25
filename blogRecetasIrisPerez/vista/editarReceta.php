<?php
// Incluyo el archivo RecetasController.php de la carpeta controlador para poder acceder a sus métodos
require_once '../controlador/RecetasController.php';

// Obtengo el id de la receta mediante la URL con el método 'GET'
$id = urldecode($_GET['id']);

// Instancio un objeto de la clase RecetasController()
$controller = new RecetasController();
// Llamo al método obtenerRecetaPorId() para obtener los datos de la receta a editar
$receta = $controller->obtenerRecetaPorId($id);

// Obtengo de la URL la búsqueda hecha para poder volver a la misma página al pulsar 'Volver'
$recetaBuscada = urldecode($_GET['receta'] ?? '');
?>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Editar receta - Blog Recetas Iris Pérez</title>
    <!-- Enlace a Bootstrap -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <!-- Enlazo el archivo css con el estilo -->
    <link rel="stylesheet" href="../estilo.css">
    <style>
        /* Estilo específico para el formulario de edición de recetas */
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
        <h1 class="text-center mb-0">Editar receta</h1>
        
        <form method="POST" action="../controlador/RecetasController.php">
            <!-- Añado un input invisible para indicar la acción en el controlador -->
            <input type="hidden" name="accion" value="editarReceta">
            <!-- Envío el id de la receta a editar al controlador -->
            <input type="hidden" name="idReceta" value="<?=$id?>">

            <!-- Muestro los datos de la receta ya escritos para que el usuario los edite -->
            <div class="mb-3">
                <label for="nombreReceta" class="form-label">Nombre de la receta:</label>
                <input type="text" class="form-control" id="nombreReceta" name="nombreReceta" value="<?= $receta['nombre'] ?>" required>
            </div>
            <div class="mb-3">
                <label for="ingredientes" class="form-label">Ingredientes:</label>
                <textarea type="text" class="form-control" id="ingredientes" name="ingredientes" rows="8" required><?= $receta['ingredientes'] ?></textarea>
            </div>
            <div class="mb-3">
                <label for="preparacion" class="form-label">Preparación:</label>
                <textarea type="text" class="form-control" id="preparacion" name="preparacion" rows="8" required><?= $receta['preparacion'] ?></textarea>
            </div>

            <div class="d-flex justify-content-center mt-3">
                <a href="../index.php?receta=<?= $recetaBuscada ?>" class="btn btn-secondary m-2" style="color: white;">Volver</a>
                <button type="submit" class="btn btn-success m-2">Guardar</button>
            </div>
        </form>
    </div>
</body>
</html>