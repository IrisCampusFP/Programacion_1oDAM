package paquete1;

import java.sql.*;
import java.util.ArrayList;

public class PeliculasDAO {

    public ArrayList<Pelicula> obtenerPeliculas() {
    	// Creo el ArrayList listaPeliculas para almacenar las peliculas que se obtengan de la base de datos
    	ArrayList<Pelicula> listaPeliculas = new ArrayList<>();

        try {
        	// Elaboro la consulta a la base de datos y la guardo en la variable query.
        	String query = "SELECT p.idPelicula, p.titulo, p.director, p.duracionMinutos, "
					+ "p.idioma, p.anoLanzamiento, c.nombre AS categoria, c.idCategoria "
					+ "FROM peliculas p "
					+ "JOIN categorias c ON p.idCategoria = c.idCategoria;";
        	
            Connection conexion = Conexion.obtenerConexion(); // Obtengo la conexión con la base de datos llamando a la clase Conexion.
            Statement stmt = conexion.createStatement(); // Creo el Statement para poder hacer la consulta.
            ResultSet rs = stmt.executeQuery(query); // Ejecuto la consulta (llamando a la variable query).

            // Para cada resultado de la consulta, obtengo todos sus datos (idPelicula, titulo, director...).
            while (rs.next()) {
                int idPelicula = rs.getInt("idPelicula");
                String titulo = rs.getString("titulo");
                String director = rs.getString("director");
                int duracion = rs.getInt("duracionMinutos");
                String idioma = rs.getString("idioma");
                int anoLanzamiento = rs.getInt("anoLanzamiento");
                
                int idCategoria = rs.getInt("idCategoria");
                String nombreCategoria = rs.getString("categoria");

                // Instancio un objeto categoria con los datos de la categoría de la pelicula
                Categoria categoria = new Categoria(idCategoria, nombreCategoria);
                // Instancio un objeto pelicula con todos los datos obtenidos (incluyendo su categoria)
                Pelicula pelicula = new Pelicula(idPelicula, titulo, director, duracion, idioma, anoLanzamiento, categoria);
                listaPeliculas.add(pelicula); // Añado la pelicula a la lista de películas
            }
            
            // Cierro el ResultSet, el Statement y la Conexión.
            rs.close();
            stmt.close();
            conexion.close();
            
        // Controlo posibles errores SQL al ejecutar la consulta
        } catch (SQLException e) {
            System.out.println("Error al obtener las películas: " + e.getMessage());
        }
        
        return listaPeliculas; // Devuelvo la lista de películas con los resultados obtenidos de la base de datos almacenados en ella.
    }

}
