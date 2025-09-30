package paquete1;

import java.util.ArrayList;

public class GestionPeliculas {
	// ArrayList en el que almacenaré las películas (obtenidas de la base de datos mediante PeliculasDAO)
    ArrayList<Pelicula> listaPeliculas = new ArrayList<>();
    PeliculasDAO DAO = new PeliculasDAO(); // Instancio un objeto de la clase PeliculasDAO para poder acceder a sus métodos

    public void verPeliculas() {
    	// Obtengo la lista de películas de base de datos y la almaceno en la variable listaPeliculas.
        listaPeliculas = DAO.obtenerPeliculas();
        
        // Recorro la lista de películas y muestro todos los datos de cada una de ellas usando printf.
        for (Pelicula p : listaPeliculas) {
            System.out.printf("ID: %d, Titulo: %s, Director: %s, Duracion: %d min., Idioma: %s, Año de Lanzamiento: %d, Categoría: %s\n",
                p.idPelicula,
                p.titulo,
                p.director,
                p.duracionMinutos,
                p.idioma,
                p.anoLanzamiento,
                p.categoria.nombre);
        }
    }
}
