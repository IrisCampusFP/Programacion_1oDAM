<?php
require_once __DIR__ . '/../modelo/class_IA.php';
require_once __DIR__ . '/../modelo/class_receta.php';


class RecetasController {
    // Creo dos atributos privados, uno para la clase IA y otro para la clase receta
    private $IA;
    private $receta;

    public function __construct() {
        // Creo un objeto de la clase IA y lo asigno a la variable (atributo privado) IA
        $this->IA = new IA();
        // Creo un objeto de la clase Receta y lo asigno a la variable (atributo privado) receta
        $this->receta = new Receta();
    }


    // 1. Generar la receta con la IA:
    // 1. A) Genero los ingredientes
    public function generarIngredientes($nombreReceta) {
        // Llamo al método generarIngredientes de la clase IA mediante el objeto IA
        return $this->IA->generarIngredientes($nombreReceta);
    }
    // 1. B) Genero el procedimiento de preparación
    public function generarPreparacion($nombreReceta) {
        // Llamo al método generarPreparacion de la clase IA mediante el objeto IA
        return $this->IA->generarPreparacion($nombreReceta);
    }

    // 1. C) Genero el nombre de la receta
    public function obtenerNombreReceta($nombreReceta) {
        // Llamo al método obtenerNombreReceta de la clase IA mediante el objeto IA
        return $this->IA->obtenerNombreReceta($nombreReceta);
    }


    // 2. Guardar la receta en la base de datos
    public function registrarReceta($nombreReceta, $ingredientes, $preparacion) {
        // Llamo al método registrarReceta de la clase Receta mediante el objeto receta
        $this->receta->registrarReceta($nombreReceta, $ingredientes, $preparacion);
    }


    // 3. Obtener la receta de la base de datos
    public function obtenerRecetas($nombreReceta) {
        // Llamo al método obtenerRecetas de la clase Receta mediante el objeto receta
        return $this->receta->obtenerRecetas($nombreReceta);
    }




    // Método para editar la receta
    public function editarReceta($idReceta, $nombreReceta, $ingredientes, $preparacion) {
        // Llamo al método editarReceta de la clase Receta mediante el objeto receta
        $this->receta->editarReceta($idReceta, $nombreReceta, $ingredientes, $preparacion);
    }

    // Método para obtener los datos de una receta mediante su ID
    public function obtenerRecetaPorId($idReceta) {
        // Llamo al método obtenerRecetaPorId de la clase Receta mediante el objeto receta
        return $this->receta->obtenerRecetaPorId($idReceta);
    }



    // Método que elimina una receta mediante su id
    public function eliminarReceta($idReceta) {
        // Llamo al método eliminarReceta de la clase Receta mediante el objeto receta
        $this->receta->eliminarReceta($idReceta);
    }
} 

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $controller = new RecetasController();

    /* Para cada acción, paso los valores recibidos del formulario al método 
    correspondiente para así ejecutar la acción deseada en la base de datos,
    después, redirijo al receta con header. */

    if ($_POST['accion'] == 'buscarReceta') {
        // 1. Genero la receta con la IA
        // 1. A) Genero los ingredientes y los guardo en una variable
        $ingredientes = $controller->generarIngredientes($_POST['recetaBuscada']);
        // 1. B) Genero la preparación y la guardo en una variable
        $preparacion = $controller->generarPreparacion($_POST['recetaBuscada']);
        // 1. C) Genero el nombre de la receta y lo guardo en una variable
        $nombreRecetaGenerada = $controller->obtenerNombreReceta($_POST['recetaBuscada']);

        // 2. Guardo la receta en la BDD
        // Registro la receta en la base de datos llamando al método registrarReceta con las variables con los datos generados
        $controller->registrarReceta(
            $nombreRecetaGenerada,
            $ingredientes,
            $preparacion
        );

        // 3. Obtengo la receta de la BDD para mostrarsela al usuario
        $controller->obtenerRecetas($_POST['recetaBuscada']);
        // Redirijo al usuario al index mostrándole la receta generada
        header("Location: ../index.php?receta=" . $nombreRecetaGenerada);
        exit();
    }
    elseif ($_POST['accion'] == 'editarReceta') {
        // Llamo al método editarReceta del controlador y le paso los datos recibidos del formulario
        $controller->editarReceta(
            $_POST['idReceta'],
            $_POST['nombreReceta'],
            $_POST['ingredientes'],
            $_POST['preparacion']
        );
        // Redirijo al usuario al index mostrándole la receta editada
        header("Location: ../index.php?receta=" . $_POST['nombreReceta']);
        exit();
    }
    elseif ($_POST['accion'] == 'eliminarReceta') {
        // Llamo al método eliminarReceta del controlador y le paso el id de la receta a eliminar
        $controller->eliminarReceta($_POST['idReceta']);
        header("Location: ../index.php?receta=" . $_POST['recetaBuscada']);
        exit();
    }
}
?>