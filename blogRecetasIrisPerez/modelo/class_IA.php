<?php
// __DIR__ obtiene el directorio en el que está ubicado el archivo
require_once __DIR__ . '/../config/class_conexion.php';

class IA {
    private $contexto;

    // Método para actualizar el contexto y añadir la ultima interacción.
    private function actualizarContexto($respuestaIA) {
        $this->contexto .= "- $respuestaIA\n\n";
    }

    // Método para reestablecer el contexto después de cada receta
    private function reestablecerContexto() {
        $this->contexto = "";
    }


    // Llamo al método que consulta a la IA enviándole un prompt para obtener los ingredientes de la receta
    public function generarIngredientes($nombreReceta) {
        $prompt = "Proporciona únicamente la lista de ingredientes de la receta: $nombreReceta.
        Sigue este formato exacto:
        - Ingrediente 1 (cantidad)
        - Ingrediente 2 (cantidad)
        - Ingrediente 3 (cantidad)
        (y así sucesivamente).
        La respuesta debe seguir exactamente este formato sin variaciones en cada consulta, 
        sin importar cuántas veces se repita la pregunta. No agregues explicaciones, encabezados, notas, 
        introducciones ni conclusiones. Solo devuelve la lista estrictamente en el formato especificado.
        Tampoco uses caracteres en negrita ni emoticonos. No olvides los guiones (- ).";
        
        // Hago la consulta a la IA
        $respuestaIA = $this->consultarIA($prompt);

        // Guardo la interacción en el contexto
        $this->actualizarContexto($respuestaIA);

        return $respuestaIA;
    }


    // Llamo al método que consulta a la IA enviándole un prompt para obtener el procedimiento de preparación de la receta
    public function generarPreparacion($nombreReceta) {
        $prompt = "Contexto previo: " . $this->contexto . "CONSULTA:\n 
        Proporciona únicamente el procedimiento de preparación de la receta $nombreReceta.
        Tienes los ingredientes en el contexto previo, ahora necesito que me des el procedimiento 
        paso a paso para preparar la receta, NADA MÁS.
        Sigue este formato exacto:
        • Paso 1:
        explicación (tiempo, temperatura, etc.)

        • Paso 2:
        explicación (tiempo, temperatura, etc.)

        (y así sucesivamente).

        La respuesta debe seguir exactamente este formato sin variaciones en cada consulta, sin importar 
        cuántas veces se repita la pregunta. No agregues introducciones, títulos, notas, ni conclusiones. 
        Solo devuelve los pasos en el formato indicado, respetando los puntos, sangrías y saltos de línea 
        especificados. Tampoco uses caracteres en negrita ni emoticonos.
        Trata de hacer una explicación del procedimiento concisa, clara y fácil de seguir sin que falte
        sin que falte ningún detalle de la receta.";

        // Hago la consulta a la IA
        $respuestaIA = $this->consultarIA($prompt);

        // Reestablezco el contexto ya que este prompt lleva el contexto anterior incluido
        $this->reestablecerContexto();

        // Guardo la interacción en el contexto
        $this->actualizarContexto($respuestaIA);

        return $respuestaIA;
    }


    public function obtenerNombreReceta($nombreReceta) {
        $prompt = "Contexto previo: " . $this->contexto . "CONSULTA:\n
        Devuelve ÚNICAMENTE el nombre exacto y formal de la receta que corresponde a la descripción de ingredientes y procedimiento en el contexto previo
        que me has proporcionado cuando te he preguntado por '$nombreReceta'.\n
        ### Importante:\n
        - El nombre debe ser el más preciso y comúnmente aceptado para esta receta en español de España.\n
        - El nombre debe contener exactamente las palabras: $nombreReceta. Puedes extender el nombre con el objetivo de que sea más descriptivo de acuerdo a la información del contexto previo. Ejemplo: $nombreReceta de ... o $nombreReceta con ... o $nombreReceta + adjetivo (hazlo solo si crees que es necesario especificar más para que se entienda cuál es la receta exacta del contexto previo y hazlo solo si estás seguro de que el nombre extendido corresponde con la receta y utilizado de forma común)\n
        - Escribe SOLO el nombre, sin explicaciones, sin caracteres especiales, sin negritas, sin emojis, sin adornos ni comentarios.\n
        - No realices invenciones, abreviaturas o combinaciones extrañas de palabras.\n
        - No pongas punto final (.) después del nombre\n
        - No sobrepases los 100 caracteres.";

        // Hago la consulta a la IA
        $respuesta = $this->consultarIA($prompt);

        // Reestablezco el contexto para la siguiente receta
        $this->reestablecerContexto();

        return $respuesta;
    }


    // Método que hace una consulta a la IA con el prompt que recibe y devuelve la respuesta
    public function consultarIA($prompt) {
        // 1. Configuración.
        // Defino el puerto 
        $puerto = '1234';

        // Construyo la URL local. 
        // Dado que LM Studio se ejecuta en local, uso 'localhost'.
        // (Hay que asegurarse de que LM Studio esté corriendo en el puerto 8000).
        $url = "http://localhost:$puerto/v1/chat/completions";  // (Asegurarse de que este endpoint coincide con el expuesto por LM Studio).


        // 2. Preparar los datos a enviar.
        // Creo un array con la información que quiero enviar al modelo.
        // En este ejemplo, enviamos un mensaje (prompt) y configuramos un parámetro como el número máximo de tokens.

        $datos = array(
            "model"=> "llama-3.2-1b-instruct",
            "messages"=> 
            array(
                array("role"=> "system", "content"=> "Responde siempre en español de España"),
                array("role"=> "user", "content"=> $prompt) // Envío un mensaje a la IA con el prompt recibido
            ),
            "temperature"=> 0.7, // Creatividad (0.1 - 0.3: baja | 0.5 - 0.7 media | 0.8 - 1.5 alta)
            "max_tokens"=> -1, // Establezco el número máximo de tokens (-1 = sin límite / cantidad máxima de tokens posible)
            "stream"=> false // El modelo genera la respuesta y luego la envía (true: la respuesta se va enviando según se genera, como en ChatGPT)
        );

        // Convierto el array a formato JSON.
        $jsonDatos = json_encode($datos);


        // 3. Inicializar cURL para preparar la petición.
        $ch = curl_init($url);


        // 4. Configurar cURL:
        curl_setopt($ch, CURLOPT_POST, true); // Establezco que se va a usar el método POST.
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true); // Indico que la respuesta se guarde en una variable en lugar de mostrarse directamente.
        curl_setopt($ch, CURLOPT_POSTFIELDS, $jsonDatos); // Envío el cuerpo de la petición con los datos en formato JSON.


        // 5. Configurar las cabeceras HTTP necesarias.
        // (Es fundamental indicar que el contenido enviado es de tipo JSON).
        curl_setopt($ch, CURLOPT_HTTPHEADER, array(
            'Content-Type: application/json',
            'Content-Length: ' . strlen($jsonDatos)
        ));

        // 6. Ejecutar la petición y capturar la respuesta del servidor.
        $respuesta = curl_exec($ch);

        // 7. Comprobar si se produjo algún error en la comunicación.
        if (curl_errno($ch)) {
            echo 'Error en cURL: ' . curl_error($ch);
        } else {
            // Muestro la respuesta recibida de LM Studio.
            $data = json_decode($respuesta, true);

            // Accedo al contenido del mensaje y lo guardo en la variable $message
            $message = $data['choices'][0]['message']['content'];

            // 8. Cierro la sesión cURL para liberar recursos.
            curl_close($ch);

            // Devuelvo el mensaje recibido (respuesta de la IA)
            return $message;
        }
    }
}
?>